/*
 *   Copyright (C) 2012-2017 Christian P. Lerch, Vienna, Austria.
 *
 *   This file is part of DSLtools - a suite of software tools for effective
 *   DSL implementations.
 *
 *   DSLtools is free software: you can use, modify and redistribute it under
 *   the terms of the GNU General Public License version 3 as published by
 *   the Free Software Foundation, Inc. <http://fsf.org/>
 *
 *   DSLtools is distributed in the hope that it will be useful, but WITHOUT
 *   ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 *   FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License
 *   version 3 for details.
 *
 *   You should have received a copy of the GNU General Public License along
 *   with this distribution. If not, see <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package kmworks.dsltools.parser.base;

import kmworks.util.StringUtil;
import xtc.parser.ParseError;
import xtc.parser.ParserBase;
import xtc.parser.Result;

import java.io.IOException;
import java.io.Reader;
import kmworks.util.StringEscapeUtil;
import kmworks.util.cp.CodepointPredicate;

/**
 *
 * @author Christian P. Lerch
 * @version 1.0.0
 * @since 1.0
 */
public abstract class AbstractParser extends ParserBase {

    private static final String DEFAULT_PARSER_ID = "_DEFAULT";
    
    private String parserId = DEFAULT_PARSER_ID;
    

//<editor-fold defaultstate="collapsed" desc="Constructors">
    /**
     * Create a new Rats! parser.
     *
     * @param reader The source reader.
     * @param fileName The name of the source file (may be blank).
     */
    public AbstractParser(final Reader reader, final String fileName) {
        this(reader, fileName, 0);
    }

    /**
     * Create a new Rats! parser.
     *
     * @param reader The source reader.
     * @param fileName The name of the source file (may be blank).
     * @param size The number of characters to be parsed.
     */
    public AbstractParser(final Reader reader, final String fileName, final int size) {
        super(reader, fileName, size);
    }

//</editor-fold>

    //
    //  Runner methods
    //

    public abstract Result pStart(final int yyStart) throws IOException;

    public Result parse() throws IOException {
        return parse(0);
    }

    public final Result parse(final int yyStart) throws IOException {
        return pStart(yyStart);
    }

    //
    //  Getter & Setter
    //

    public void setParserId(String parserId) {
        this.parserId = parserId;
    }
    
    public String getParserId() {
        return parserId;
    }
    
    //
    //  Parser predicates
    //

    protected boolean isInvalidStringCharacter(char c) {
        final CodepointPredicate iscsDefault = CodepointSets.DEFAULT$INVALID_STRING_CHAR_SET;
        if (parserId.equals(DEFAULT_PARSER_ID)) {
            return iscsDefault.contains(c);
        } else {
            CodepointPredicate iscs = CodepointSets.get(parserId + "$INVALID_STRING_CHAR_SET");
            return iscs==null ? iscsDefault.contains(c) : iscs.contains(c);
        }
    }

    //
    //  Parser utilities
    //
    
    protected String unescape(String s) {
        return StringEscapeUtil.unescapeJava(s);
    }

    public String fmtErrorMsg(String source, ParseError error) {
        /*
         * Find char-index of beginning of line in error
         */
        // start at min(err.index, source.length)
        int bol = error.index >= source.length() ? source.length() - 1 : error.index;
        assert bol < source.length() && bol >= 0;
        // find first char of current line
        while (bol >= 0 && source.charAt(bol) != Character.LINE_SEPARATOR) {
            bol--;
        }
        assert bol < source.length() && bol >= -1;
        /*
         * Retrieve and measure line in error
         */
        String errorLine;
        try {
            errorLine = lineAt(error.index);
        } catch (IOException ex) {
            errorLine = "Sorry, line in error cannot be located";
        }

        final int errorInLine = error.index - bol - 1;
        final int tabsCount = StringUtil.countOccurencesOf(errorLine, "\t");
        final int lineNr = StringUtil.countNewlines(source.substring(0, bol + errorInLine - 1)) + 1;

        /*
         * build-up error msg
         */
        final StringBuilder sb = new StringBuilder();
        sb.append("ERROR in line ").append(lineNr).append(": ");
        sb.append(error.msg).append("\n\n");
        sb.append(errorLine).append("\n");
        for (int i = 0; i < errorInLine + tabsCount * 5; i++) {
            sb.append('-');
        }
        sb.append('^').append("\n");
        return sb.toString();
    }
}
