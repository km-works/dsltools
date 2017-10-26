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

import com.google.common.io.Files;
import java.io.File;
import java.nio.charset.StandardCharsets;
import kmworks.dsltools.parser.base.ParserUtil;
import kmworks.dsltools.util.xml.XMLtoATDL_Serializer;
import kmworks.dsltools.util.xml.XSLTransformerFactory;
import kmworks.util.tuple.Tuple2;
import nu.xom.Document;
import nu.xom.Element;
import static org.junit.Assert.*;
import org.junit.Test;
import xtc.parser.Result;


/**
 *
 * @author Christian P. Lerch
 */
public class PEG_ParserTest {
    
    private static final String TEST_FILE_DIR = "src/test/resources/kmworks/dsltools/parser/ml/";
    
    public PEG_ParserTest() {
    }

    @Test
    public void test_ML_File_PEG() throws Exception {
        String fileName = "PEG";
        Tuple2<String, String> result = ParserTestUtil.parsePEGFile(new File(TEST_FILE_DIR + fileName + ".peg"));
        File atdlResultFile = new File(TEST_FILE_DIR + fileName + ".ast.atdl");
        File xmlResultFile = new File(TEST_FILE_DIR + fileName + ".ast.xml");
        //* Files.asCharSink(atdlResultFile, StandardCharsets.UTF_8).write(result._1());  // uncomment this once whenever the AST has changed
        //* Files.asCharSink(xmlResultFile, StandardCharsets.UTF_8).write(result._2());   // uncomment this once whenever the AST has changed
        assertEquals(result._1(), Files.asCharSource(atdlResultFile, StandardCharsets.UTF_8).read());
    }
    
    @Test
    public void test_ML_File_01() throws Exception {    
        // src: EscapedClassChars <- [\-\[\\\]]     // == ['-','[','\\',']']
        Tuple2<String, String> result = ParserTestUtil.parsePEGFile(new File(TEST_FILE_DIR + "ML_File_01.peg"));
        assertEquals(result._1(), "(Grammar (Production @name=\"EscapedClassChars\" (Choice (Sequence (CharClass @value=\"[[\\\\-], [\\\\[], [\\\\\\\\], [\\\\]]]\" @caption=\"[-[\\\\]]\" (CharRange @first=\"-\") (CharRange @first=\"[\") (CharRange @first=\"\\\\\") (CharRange @first=\"]\"))))))");
    }
    
    @Test
    public void test_ML_File_02() throws Exception {    
        // src: CodePoint <- EscapeSequence / ![\\\-\]] DOT
        Tuple2<String, String> result = ParserTestUtil.parsePEGFile(new File(TEST_FILE_DIR + "ML_File_02.peg"));
        assertEquals(result._1(), "(Grammar (Production @name=\"CodePoint\" (Choice (Sequence (NonTerminal @name=\"EscapeSequence\")) (Sequence (Predicate @type=\"MustNotMatch\" (CharClass @value=\"[[\\\\\\\\], [\\\\-], [\\\\]]]\" @caption=\"[\\\\-]]\" (CharRange @first=\"\\\\\") (CharRange @first=\"-\") (CharRange @first=\"]\"))) (NonTerminal @name=\"DOT\")))))");
    }
    
    @Test
    public void test_ML_File_03() throws Exception {    
        Tuple2<String, String> result = ParserTestUtil.parsePEGFile(new File(TEST_FILE_DIR + "ML_File_03.peg"));
        assertEquals(result._1(), "(Grammar (Production @name=\"StringLiteral\" (Choice (Sequence (NonTerminal @name=\"EscapedStringLiteral\")))) (Production @name=\"EscapedStringLiteral\" (Choice (Sequence (NonTerminal @name=\"QuotedStringLiteral\")))) (Production @name=\"QuotedStringLiteral\" (Choice (Sequence (CharClass @value=\"[[\\\"]]\" @caption=\"[\\\"]\" (CharRange @first=\"\\\"\")) (Multiple @multiplicity=\"ZeroOrMore\" (Choice (Sequence (CharClass @value=\"[[\\\\\\\\]]\" @caption=\"[\\\\]\" (CharRange @first=\"\\\\\")) (CharClass @value=\"[[\\\"], [\\\\\\\\]]\" @caption=\"[\\\"\\\\]\" (CharRange @first=\"\\\"\") (CharRange @first=\"\\\\\"))) (Sequence (Predicate @type=\"MustNotMatch\" (CharClass @value=\"[[\\\"]]\" @caption=\"[\\\"]\" (CharRange @first=\"\\\"\"))) (Predicate @type=\"MustNotMatch\" (NonTerminal @name=\"InvalidStringContent\")) (NonTerminal @name=\"_\")))) (CharClass @value=\"[[\\\"]]\" @caption=\"[\\\"]\" (CharRange @first=\"\\\"\"))) (Sequence (CharClass @value=\"[[']]\" @caption=\"[']\" (CharRange @first=\"'\")) (Multiple @multiplicity=\"ZeroOrMore\" (Choice (Sequence (CharClass @value=\"[[\\\\\\\\]]\" @caption=\"[\\\\]\" (CharRange @first=\"\\\\\")) (CharClass @value=\"[['], [\\\\\\\\]]\" @caption=\"['\\\\]\" (CharRange @first=\"'\") (CharRange @first=\"\\\\\"))) (Sequence (Predicate @type=\"MustNotMatch\" (CharClass @value=\"[[']]\" @caption=\"[']\" (CharRange @first=\"'\"))) (Predicate @type=\"MustNotMatch\" (NonTerminal @name=\"InvalidStringContent\")) (NonTerminal @name=\"_\")))) (CharClass @value=\"[[']]\" @caption=\"[']\" (CharRange @first=\"'\"))))) (Production @name=\"CodePointRange\" (Choice (Sequence (NonTerminal @name=\"CodePoint\") (Literal @caption=\"-\") (NonTerminal @name=\"CodePoint\")) (Sequence (NonTerminal @name=\"CodePoint\")))) (Production @name=\"CodePoint\" (Choice (Sequence (NonTerminal @name=\"EscapeSequence\")) (Sequence (Predicate @type=\"MustNotMatch\" (CharClass @value=\"[[\\\\\\\\], [\\\\-], [\\\\]]]\" @caption=\"[\\\\-]]\" (CharRange @first=\"\\\\\") (CharRange @first=\"-\") (CharRange @first=\"]\"))) (AnyChar)))) (Production @name=\"EscapeSequence\" (Choice (Sequence (Literal @caption=\"\\\\\\\\\") (CharClass @value=\"[[b], [t], [n], [f], [r], [\\\"], ['], [\\\\-], [\\\\[], [\\\\\\\\], [\\\\]]]\" @caption=\"[btnfr\\\"'-[\\\\]]\" (CharRange @first=\"b\") (CharRange @first=\"t\") (CharRange @first=\"n\") (CharRange @first=\"f\") (CharRange @first=\"r\") (CharRange @first=\"\\\"\") (CharRange @first=\"'\") (CharRange @first=\"-\") (CharRange @first=\"[\") (CharRange @first=\"\\\\\") (CharRange @first=\"]\"))) (Sequence (NonTerminal @name=\"UnicodeEscape\")))) (Production @name=\"UnicodeEscape\" (Choice (Sequence (Literal @caption=\"\\\\\\\\\") (Literal @caption=\"u\") (NonTerminal @name=\"HexQuad\")))) (Production @name=\"HexQuad\" (Choice (Sequence (NonTerminal @name=\"HexDigit\") (NonTerminal @name=\"HexDigit\") (NonTerminal @name=\"HexDigit\") (NonTerminal @name=\"HexDigit\")))) (Production @name=\"HexDigit\" (Choice (Sequence (CharClass @value=\"[[0, 9], [a, f], [A, F]]\" @caption=\"[0-9a-fA-F]\" (CharRange @first=\"0\" @last=\"9\") (CharRange @first=\"a\" @last=\"f\") (CharRange @first=\"A\" @last=\"F\"))))))");
    }
    
    @Test
    public void test_ML_File_04() throws Exception {    
        Tuple2<String, String> result = ParserTestUtil.parsePEGFile(new File(TEST_FILE_DIR + "ML_File_04.peg"));
        assertEquals(result._1(), "(Grammar (Production @name=\"Grammar\" (Choice (Sequence (NonTerminal @name=\"w\") (Multiple @multiplicity=\"OneOrMore\" (NonTerminal @name=\"Definition\")) (NonTerminal @name=\"EOI\")))) (Production @name=\"Definition\" (Choice (Sequence (NonTerminal @name=\"Identifier\") (NonTerminal @name=\"DEFINES\") (NonTerminal @name=\"Expression\")))) (Production @name=\"Expression\" (Choice (Sequence (NonTerminal @name=\"Sequence\") (Multiple @multiplicity=\"ZeroOrMore\" (Choice (Sequence (NonTerminal @name=\"ALT\") (NonTerminal @name=\"Sequence\"))))))) (Production @name=\"Sequence\" (Choice (Sequence (Multiple @multiplicity=\"ZeroOrMore\" (NonTerminal @name=\"PrefixTerm\"))))) (Production @name=\"PrefixTerm\" (Choice (Sequence (Multiple @multiplicity=\"ZeroOrOne\" (Choice (Sequence (NonTerminal @name=\"AND\")) (Sequence (NonTerminal @name=\"NOT\")))) (NonTerminal @name=\"Term\")))) (Production @name=\"Term\" (Choice (Sequence (NonTerminal @name=\"Primary\") (Multiple @multiplicity=\"ZeroOrOne\" (Choice (Sequence (NonTerminal @name=\"OPTION\")) (Sequence (NonTerminal @name=\"STAR\")) (Sequence (NonTerminal @name=\"PLUS\"))))))))");
    }
    
    @Test
    public void test_Rats_PEG() throws Exception {    
        Tuple2<String, String> result = ParserTestUtil.parsePEGFile(new File(TEST_FILE_DIR + "rats.peg"));
        File atdlResultFile = new File(TEST_FILE_DIR + "rats.ast.atdl");
        File xmlResultFile = new File(TEST_FILE_DIR + "rats.ast.xml");
        //* Files.asCharSink(atdlResultFile, StandardCharsets.UTF_8).write(result._1());  // uncomment this once whenever the AST has changed
        //* Files.asCharSink(xmlResultFile, StandardCharsets.UTF_8).write(result._2());   // uncomment this once whenever the AST has changed
        assertEquals(result._1(), Files.asCharSource(atdlResultFile, StandardCharsets.UTF_8).read());
    }
    
    @Test
    public void test_ML_String_01() throws Exception {  
        // string source: backslashs must be double escaped
        Tuple2<String, String> result = ParserTestUtil.parsePEGString("EscapedClassChars <- [\\-\\[\\\\\\]]");           // == ['-','[','\\',']']
        assertEquals(result._1(), "(Grammar (Production @name=\"EscapedClassChars\" (Choice (Sequence (CharClass @value=\"[[\\\\-], [\\\\[], [\\\\\\\\], [\\\\]]]\" @caption=\"[-[\\\\]]\" (CharRange @first=\"-\") (CharRange @first=\"[\") (CharRange @first=\"\\\\\") (CharRange @first=\"]\"))))))");
    }
    
    @Test
    public void test_ML_String_02() throws Exception {
        Tuple2<String, String> result = ParserTestUtil.parsePEGString("CodePoint <- EscapeSequence / ![\\\\\\-\\]] DOT");
        assertEquals(result._1(), "(Grammar (Production @name=\"CodePoint\" (Choice (Sequence (NonTerminal @name=\"EscapeSequence\")) (Sequence (Predicate @type=\"MustNotMatch\" (CharClass @value=\"[[\\\\\\\\], [\\\\-], [\\\\]]]\" @caption=\"[\\\\-]]\" (CharRange @first=\"\\\\\") (CharRange @first=\"-\") (CharRange @first=\"]\"))) (NonTerminal @name=\"DOT\")))))");
    }
    
    @Test
    public void test_ML_String_03() throws Exception {
        Tuple2<String, String> result = ParserTestUtil.parsePEGString("Space <- '\\t' / '\\f' / ' ' / EOL");
        assertEquals(result._1(), "(Grammar (Production @name=\"Space\" (Choice (Sequence (Literal @caption=\"\\\\t\")) (Sequence (Literal @caption=\"\\\\f\")) (Sequence (Literal @caption=\" \")) (Sequence (NonTerminal @name=\"EOL\")))))");
    }
    
    @Test
    public void test_ML_String_04() throws Exception {
        Tuple2<String, String> result = ParserTestUtil.parsePEGString("Term <-  A? B");
        assertEquals(result._1(), "(Grammar (Production @name=\"Term\" (Choice (Sequence (Multiple @multiplicity=\"ZeroOrOne\" (NonTerminal @name=\"A\")) (NonTerminal @name=\"B\")))))");
    }
    
    @Test
    public void test_ML_String_05() throws Exception {
        Tuple2<String, String> result = ParserTestUtil.parsePEGString("A <- B C D \\\n E F G");
        assertEquals(result._1(), "(Grammar (Production @name=\"A\" (Choice (Sequence (NonTerminal @name=\"B\") (NonTerminal @name=\"C\") (NonTerminal @name=\"D\" @an:linebreakAfter=\"true\") (NonTerminal @name=\"E\") (NonTerminal @name=\"F\") (NonTerminal @name=\"G\")))))");
    }
    
    @Test
    public void test_ML_String_06() throws Exception {
        Result parseResult = ParserUtil.parseString("PEG", "Term <-  Primary ( OPTION / STAR / PLUS )?");
        Element grammar = parseResult.semanticValue();
        Document doc = new Document(grammar);
        Document doc2 = XSLTransformerFactory.doTransform(doc, "ML-canonical");
        String atdlResult = XMLtoATDL_Serializer.write(doc2.getRootElement());
        assertEquals(atdlResult, "(Grammar (Production @name=\"Term\" (Choice (Sequence (NonTerminal @name=\"Primary\") (Choice @epsilon=\"true\" (Sequence (NonTerminal @name=\"OPTION\")) (Sequence (NonTerminal @name=\"STAR\")) (Sequence (NonTerminal @name=\"PLUS\")))))))");
 }
    
}
