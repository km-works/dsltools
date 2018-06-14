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
package kmworks.dsltools.parser;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import com.google.common.io.Files;
import kmworks.dsltools.parser.base.ParserUtil;
import kmworks.dsltools.util.xml.XMLtoATDL_Serializer;
import nu.xom.Document;
import nu.xom.Element;
import xtc.parser.Result;

/**
 *
 * @author Christian P. Lerch
 */
public final class ParserTestUtil {

    // TODO: Review & merge with ParserUtil
    
    public static String parse2XML(String syntaxType, String srcString) {
        Result parseResult;
        String xmlResult;
        long end, begin=System.nanoTime();
        try {
            parseResult = ParserUtil.parseString(syntaxType, srcString);
            end = System.nanoTime();
            Element grammar = parseResult.semanticValue();
            Document doc = new Document(grammar);
            xmlResult = doc.toXML();
        } catch (Exception ex) {
            System.out.println("ERROR: " + ex.getMessage());
            return null;
        }
        printResults(xmlResult, end-begin);
        return xmlResult;
    }

    public static String parse2ATDL(String syntaxType, String srcString) {
        Result parseResult;
        String atdlResult;
        long end, begin=System.nanoTime();
        try {
            parseResult = ParserUtil.parseString(syntaxType, srcString);
            end = System.nanoTime();
            Element grammar = parseResult.semanticValue();
            Document doc = new Document(grammar);
            atdlResult = XMLtoATDL_Serializer.write(doc.getRootElement());
        } catch (Exception ex) {
            System.out.println("ERROR: " + ex.getMessage());
            return null;
        }
        printResults(atdlResult, end-begin);
        return atdlResult;
    }

    public static String parse2XML(String syntaxType, File srcFile) {
        try {
            return parse2XML(syntaxType, Files.asCharSource(srcFile, StandardCharsets.UTF_8).read());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String parse2ATDL(String syntaxType, File srcFile) {
        try {
            return parse2ATDL(syntaxType, Files.asCharSource(srcFile, StandardCharsets.UTF_8).read());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private static void printResults(String result, long nanos) {
        System.out.println();
        System.out.println(result);
        System.out.println("Parse-Time: " + nanos/1000000L + "ms");
        System.out.println();
    }

}
