/*
 *   Copyright (C) 2012 Christian P. Lerch, Vienna, Austria.
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
 *   with this distribution. If not, see <http://km-works.eu/res/GPL.txt> or
 *   <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package kmworks.dsltools.parser.base;

import kmworks.dsltools.parser.adt.ADT_Parser;
//import kmworks.dsltools.parser.ml.EBNF_Parser;
//import kmworks.dsltools.parser.ml.EBNF_W3C_Parser;
import kmworks.dsltools.parser.ml.PEG_Parser;
import kmworks.util.lambda.Function3;
import xtc.parser.ParserBase;

import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Christian P. Lerch
 */
public final class ParserFactory {

    private static final ParserFactory INSTANCE = new ParserFactory();

    private final Map<String, Function3<Reader, String, Integer, ? extends AbstractParser>> parserRegistry =
            new HashMap<>();

    private ParserFactory() {
        parserRegistry.put("ADT", (r, f, s) -> new ADT_Parser(r, f, s));
        parserRegistry.put("PEG", (r, f, s) -> new PEG_Parser(r, f, s));
    }

    public static void registerParser(String syntaxType,
                                      Function3<Reader, String, Integer, ? extends AbstractParser> parserMaker) {
        INSTANCE.parserRegistry.put(syntaxType, parserMaker);
    }

    public static AbstractParser newParser(final String syntaxType, final Reader reader) {
        return newParser(syntaxType, reader, ParserBase.INIT_SIZE - 1);
    }

    public static AbstractParser newParser(final String syntaxType, final Reader reader, final long size) {
        return newParser(syntaxType, reader, size, "SRC");
    }

    public static AbstractParser newParser(final String syntaxType,
                                           final Reader reader, final long size, final String fileName) {
        return INSTANCE.createParser(syntaxType, reader, size, fileName);
    }

    private AbstractParser createParser(
            final String syntaxType, final Reader reader, final long size, final String fileName) {

        AbstractParser parser = parserRegistry.get(syntaxType).apply(reader, fileName, (int) size);

        if (parser == null) {
            throw new IllegalArgumentException("No parser for syntax-type: " + syntaxType);
        }

        return parser;
    }
}
