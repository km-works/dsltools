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
import xtc.parser.ParserBase;

import java.io.Reader;

/**
 *
 * @author Christian P. Lerch
 */
public final class ParserFactory {

    public ParserFactory() {
    }

    public static AbstractParser newParser(final String language, final Reader reader) {
        return newParser(language, reader, ParserBase.INIT_SIZE - 1);
    }

    public static AbstractParser newParser(final String language, final Reader reader, final long size) {
        return newParser(language, reader, size, "SRC");
    }

    public static AbstractParser newParser(final String language, final Reader reader, final long size, final String fileName) {

        AbstractParser parser;

        switch (language) {
            case "ADT":
                parser = new ADT_Parser(reader, fileName, (int) size);
                //parser.setParserId("ADT");              // -> ADT_INVALID_STRING_CHAR_SET should be registered
                break;
            //case "EBNF":
            //    parser = new EBNF_Parser(reader, fileName, (int)size);
            //    //parser.setParserId("ML.EBNF");        // -> ML.EBNF_INVALID_STRING_CHAR_SET should be registered
            //    break;
            //  break;
            //case "EBNF_W3C":
            //    parser = new EBNF_W3C_Parser(reader, fileName, (int)size);
            //    //parser.setParserId("ML.EBNF_W3C");    // -> ML.EBNF_W3C_INVALID_STRING_CHAR_SET should be registered
            //    break;
            case "PEG":
                parser = new PEG_Parser(reader, fileName, (int) size);
                //parser.setParserId("ML.PEG");           // -> ML.PEG_INVALID_STRING_CHAR_SET should be registered
                break;
            default:
                throw new IllegalArgumentException("No parser for language: " + language);
        }

        return parser;
    }
}
