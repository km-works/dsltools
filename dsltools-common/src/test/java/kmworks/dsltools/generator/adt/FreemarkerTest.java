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

import com.google.common.io.Files;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import java.io.File;
import java.io.FileInputStream;
import kmworks.dsltools.ast.adt.*;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import kmworks.dsltools.parser.base.ParserFactory;
import kmworks.dsltools.parser.base.AbstractParser;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import xtc.parser.Result;

/**
 * Created by cpl on 21.03.2017.
 */
public class FreemarkerTest {

    @Test
    public void testFM_genNodeVisitor() throws IOException, TemplateException {
        /* Get the template (uses cache internally) */
        Template templ = FmConfig.configuration().getTemplate("genNodeVisitor.ftl");

        File srcFile = new File("src/main/java/kmworks/dsltools/adt/adt/ADT.adt"); // ml/ML.adt   adt/ADT.adt
        assertTrue(srcFile.exists());
        
        AbstractParser parser;
        Result result;
        String errorMsg = "OK";
        boolean isError = false;
        
        try (Reader reader = new InputStreamReader(new FileInputStream(srcFile), StandardCharsets.UTF_8)) {
            parser = ParserFactory.newParser("ADT", reader, srcFile.length(), "SRC" );
            result = parser.parse();
        }
        
        Grammar grammar = null;
        
        if (result != null) {
            if (result.hasValue()) {
                grammar = result.semanticValue();
            } else {
                errorMsg = parser.fmtErrorMsg(Files.asCharSource(srcFile, StandardCharsets.UTF_8).read(), result.parseError());
                isError = true;
            }
        } else {
            errorMsg = "Parser returned no result";
            isError = true;
        }
        
        assertFalse(errorMsg, isError);

        Map<String, Object> data = new HashMap<>();
        data.put("grammar", grammar);

        /* Merge data-model with template */
        Writer out = new OutputStreamWriter(System.out, StandardCharsets.UTF_8);
        templ.process(data, out);
    }
}
