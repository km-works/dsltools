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
package kmworks.dsltools.parser.ml;

import java.io.File;
import kmworks.dsltools.parser.base.ParserUtil;
import kmworks.dsltools.util.xml.XMLtoATDL_Serializer;
import kmworks.util.tuple.Tuple;
import kmworks.util.tuple.Tuple2;
import nu.xom.Document;
import nu.xom.Element;
import xtc.parser.Result;

/**
 *
 * @author Christian P. Lerch
 */
public final class ParserTestUtil {
    
    public static Tuple2<String, String> parsePEGString(String srcString) {
        String parserName = "PEG";
        Result parseResult;
        String xmlResult;
        String atdlResult;
        long end, begin=System.nanoTime();
        try {
            parseResult = ParserUtil.parseString(parserName, srcString);
            end = System.nanoTime();
            Element grammar = parseResult.semanticValue();
            Document doc = new Document(grammar);
            xmlResult = doc.toXML();
            atdlResult = XMLtoATDL_Serializer.write(doc.getRootElement());
        } catch (Exception ex) {
            System.out.println("ERROR: " + ex.toString());
            return null;
        }
        printResults(xmlResult, atdlResult, end-begin);
        return Tuple.of(atdlResult, xmlResult);
    }
    
    public static Tuple2<String, String> parsePEGFile(File srcFile) {
        String parserName = "PEG";
        Result parseResult;
        String xmlResult;
        String atdlResult;
        long end, begin=System.nanoTime();
        try {
            parseResult = ParserUtil.parseFile(parserName, srcFile);
            end = System.nanoTime();
            Element grammar = parseResult.semanticValue();
            Document doc = new Document(grammar);
            xmlResult = doc.toXML();
            atdlResult = XMLtoATDL_Serializer.write(doc.getRootElement());
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
        printResults(xmlResult, atdlResult, end-begin);
        return Tuple.of(atdlResult, xmlResult);
    }
    
    private static void printResults(String xmlResult, String atdlResult, long nanos) {
        System.out.println();
        System.out.println(xmlResult);
        System.out.println(atdlResult);
        System.out.println("Parse-Time: " + nanos/1000000L + "ms");
        System.out.println();
    }

}
