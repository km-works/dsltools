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
package kmworks.dsltools.generator.adt;

import java.io.File;
import kmworks.dsltools.parser.base.ParserUtil;
import nu.xom.Document;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import xtc.parser.Result;

/**
 * Created by cpl on 22.03.2017.
 */
public class ADTGenTest {
    
    @Test
    public void testADTGen_ToXML() throws Exception {
        String parserName = "ADT";
        File srcFile = new File("src/main/resources/kmworks/dsltools/adt/ml/ML.adt");
        Result parseResult;
        String xmlResult = "";
        try {
            parseResult = ParserUtil.parseFile(parserName, srcFile);
            kmworks.dsltools.adt.adt.ToXML_Visitor visitor = new kmworks.dsltools.adt.adt.ToXML_Visitor();
            Document doc = visitor.visit(parseResult.semanticValue());
            xmlResult = doc.toXML();
        } catch (Exception ex) {
            System.out.println(ex.toString());
            assertTrue(false);
        }
        System.out.println(xmlResult);
        assertTrue(true);
    }
    
}
