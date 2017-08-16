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

import com.google.common.io.Files;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import xtc.parser.Result;

/**
 *
 * @author Christian P. Lerch
 */
public final class ParserUtil {
    
    private ParserUtil() {}
    
    
    public static Result parseFile(String parserName, File srcFile) throws Exception {
        Result parseResult;
        try (Reader reader = new InputStreamReader(new FileInputStream(srcFile), StandardCharsets.UTF_8)) {
            AbstractParser parser = ParserFactory.newParser(parserName, reader, srcFile.length(), srcFile.getName() );
            parseResult = parser.parse();
            if (parseResult == null) {
                throw new RuntimeException("Parser returned no result");
            }
            if (!parseResult.hasValue()) {
                String errorMsg = parser.fmtErrorMsg(Files.toString(srcFile, StandardCharsets.UTF_8), parseResult.parseError());
                throw new RuntimeException(errorMsg);
            }
        }
        assert parseResult.hasValue();
        return parseResult;
    }

    public static Result parseString(String parserName, String srcString) throws Exception {
        Result parseResult;
        try (Reader reader = new StringReader(srcString)) {
            AbstractParser parser = ParserFactory.newParser(parserName, reader, srcString.length(), "STRING" );
            parseResult = parser.parse();
            if (parseResult == null) {
                throw new RuntimeException("Parser returned no result");
            }
            if (!parseResult.hasValue()) {
                String errorMsg = parser.fmtErrorMsg(srcString, parseResult.parseError());
                throw new RuntimeException(errorMsg);
            }
        }
        assert parseResult.hasValue();
        return parseResult;
    }
    
}
