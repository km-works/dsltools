/*
 *   Copyright (C) 2012-2018 Christian P. Lerch, Vienna, Austria.
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

import kmworks.dsltools.ast.base.*;
import kmworks.util.StringPool;
import kmworks.util.StringUtil;
import xtc.parser.ParseError;
import xtc.parser.ParserBase;
import xtc.parser.Result;
import xtc.util.Pair;

import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static kmworks.util.base.Preconditions.*;
import static kmworks.dsltools.parser.base.CodepointUtils.*;

/**
 *
 * @author Christian P. Lerch
 */
public abstract class AbstractParser extends ParserBase {

    // Syntax module names
    public final static String IDENTIFIER$SYNTAX_MODULE = "IDENTIFIER_SYNTAX";
    public final static String STRING_LITERAL$SYNTAX_MODULE = "STRING_LITERAL_SYNTAX";
    public final static String NUMERALS$SYNTAX_MODULE = "NUMERALS_SYNTAX";
    public final static String WHITESPACE$SYNTAX_MODULE = "WHITESPACE_SYNTAX";

    private final Map<String, String> attributes;

//<editor-fold defaultstate="collapsed" desc="Constructors">

    /**
     * Create a new Rats! parser.
     *
     * @param reader The source reader.
     * @param fileName The name of the source file (may be blank).
     */
    protected AbstractParser(final Reader reader, final String fileName) {
        this(reader, fileName, 0);
    }

    /**
     * Create a new Rats! parser.
     *
     * @param reader The source reader.
     * @param fileName The name of the source file (may be blank).
     * @param size The number of characters to be parsed.
     */
    protected AbstractParser(final Reader reader, final String fileName, final int size) {
        super(reader, fileName, size);
        attributes = new HashMap<>();
        initParser();
    }

    protected void initParser() {};

//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Runner Methods">

    //
    //  Runner methods
    //

    public abstract Result pStart(final int yyStart) throws IOException;

    public Result parse() throws IOException {
        return parse(0);
    }

    public final Result parse(final int yyStart) throws IOException {
        Result result = pStart(yyStart);
        yyReader.close();
        return result;
    }
//</editor-fold>

    //
    //  Parser feature attributes - getter & setter
    //

    public String getAttribute(@Nonnull final String key) { return getAttribute(key, null); }

    public String getAttribute(@Nonnull final String key, @Nullable String defaultValue) {
        checkNotEmpty(key, "key " + StringPool.MUST_NOT_BE_NULL_OR_EMPTY_MSG);
        return attributes.getOrDefault(key, defaultValue);
    }

    public void setAttribute(@Nonnull final String key, @Nonnull final String value) {
        checkNotEmpty(key, "key " + StringPool.MUST_NOT_BE_NULL_OR_EMPTY_MSG);
        checkNotEmpty(value, "key " + StringPool.MUST_NOT_BE_NULL_OR_EMPTY_MSG);
        attributes.put(key, value);
    }

    public String syntaxTypeFor(@Nonnull final String syntaxModule) {
        return getAttribute(syntaxModule, Predefined.SyntaxType.DEFAULT.toString());
    }

    public void setSyntaxType(@Nonnull final String syntaxModule, Predefined.SyntaxType syntaxType) {
        setSyntaxType(syntaxModule, syntaxType.toString());
    }

    public void setSyntaxType(@Nonnull final String syntaxModule, String syntaxType) {
        setAttribute(syntaxModule, syntaxType);
    }


    //
    //    Generic syntax character test & string transformer helpers
    //

    protected boolean contains(char ch, @Nonnull final String  syntaxModule, @Nonnull final String genericSetName) {
        checkNotEmpty(genericSetName, "genericSetName " + StringPool.MUST_NOT_BE_NULL_OR_EMPTY_MSG);
        return CodepointUtils.getPredicate(mkKey(syntaxTypeFor(syntaxModule), genericSetName)).contains(ch);
    }

    protected boolean isDecDigit(char ch) {
        return contains(ch, NUMERALS$SYNTAX_MODULE, DEC_DIGITS);
    }

    protected boolean isHexDigit(char ch) {
        return contains(ch, NUMERALS$SYNTAX_MODULE, HEX_DIGITS);
    }


    protected boolean isIdentifierHeadChar(char ch) {
        return contains(ch, IDENTIFIER$SYNTAX_MODULE, IDENTIFIER_HEAD_CHARS);
    }

    protected boolean isIdentifierTailChar(char ch) {
        return contains(ch, IDENTIFIER$SYNTAX_MODULE, IDENTIFIER_TAIL_CHARS);
    }
    
    protected boolean isStringLiteralInvalidChar(char ch) {
        return contains(ch, STRING_LITERAL$SYNTAX_MODULE, STRING_LITERAL_INVALID_CHARS);
    }

    protected boolean isStringLiteralEscapedChar(char ch) {
        return contains(ch, STRING_LITERAL$SYNTAX_MODULE, STRING_LITERAL_ESCAPED_CHARS);
    }

    protected String unescapeStringLiteral(String s) {
        return CodepointUtils.getTransformer(
                mkKey(syntaxTypeFor(STRING_LITERAL$SYNTAX_MODULE), STRING_LITERAL_UNESCAPE_TRFM)).apply(s);
    }

    //
    //  Parser utilities
    //

    public String asString(Pair<Character> chars) {
        return concat(chars);
    }

    public String asString(Character... chars) {
        return concat(Arrays.asList(chars));
    }

    public String concat(Iterable<Character> charList) {
        final StringBuilder sb = new StringBuilder();
        for (char s : charList) {
            sb.append(s);
        }
        return sb.toString();
    }

    public Span mkSpan(int start) {
        return SpanImpl.of(start, yyCount-2);
    }


    public static String fmtErrorMsg(String source, ParseError error) {

        // normalize index for EOF and line terminating positions
        int index = error.index >= source.length() ? source.length() - 1 : error.index;
        if (0 > index) {
            throw new IndexOutOfBoundsException("Parser index: " + index);
        }
        if ((0 < index)
                && ('\n' == source.charAt(index))
                && ('\r' == source.charAt(index - 1))) {
            index--;
        }

        char c;
        int bol = index;
        int eol = index;

        // find the beginning of the line in error.
        assert bol < source.length() && bol >= 0;
        while (true) {
            if (0 == bol) {
                break;
            }
            c = source.charAt(bol - 1);
            if (('\r' == c) || ('\n' == c)) {
                break;
            }
            bol--;
        }
        assert bol < source.length() && bol >= 0;

        // Find the end of the line in error.
        c = source.charAt(eol);
        while (eol < source.length() - 1 && ('\r' != c) && ('\n' != c)) {
            eol++;
            c = source.charAt(eol);
        }
        assert eol < source.length();

        // Retrieve and measure line in error
        final String errorLine = bol == eol ? "" : new String(source.toCharArray(), bol, eol - bol + 1);
        final int lineNr = StringUtil.countNewlines(source.substring(0, bol)) + 1;

        // Build error msg
        final StringBuilder sb = new StringBuilder();
        sb.append("ERROR in line ").append(lineNr).append(": ");
        sb.append(error.msg).append("\n\n");
        sb.append(errorLine).append("\n");
        for (int i = 0; i < index - bol; i++) {
            sb.append('-');
        }
        sb.append('^').append("\n");
        return sb.toString();
    }

}
