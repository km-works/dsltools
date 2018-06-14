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
import java.io.StringReader;
import java.nio.charset.StandardCharsets;

import kmworks.dsltools.parser.ParserTestUtil;
import kmworks.dsltools.parser.base.AbstractParser;
import kmworks.dsltools.parser.base.ParserFactory;
import kmworks.dsltools.parser.base.ParserUtil;
import kmworks.dsltools.util.xml.XMLtoATDL_Serializer;
import kmworks.dsltools.util.xml.XSLTransformerFactory;
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
    private static final String syntaxName = "PEG";
    
    public PEG_ParserTest() {
    }

    @Test
    public void testParserInit_01() throws Exception {
        AbstractParser parser = ParserFactory.newParser("PEG", new StringReader(""), 0);
        assertEquals("XML", parser.syntaxTypeFor(AbstractParser.IDENTIFIER$SYNTAX_MODULE));
        assertEquals("JAVA", parser.syntaxTypeFor(AbstractParser.STRING_LITERAL$SYNTAX_MODULE));
        assertEquals("DEFAULT", parser.syntaxTypeFor(AbstractParser.NUMERALS$SYNTAX_MODULE));
        assertEquals("PEG", parser.syntaxTypeFor(AbstractParser.WHITESPACE$SYNTAX_MODULE));
    }

    @Test   // parse PEG syntax
    public void test_ML_File_PEG() throws Exception {
        String atdlResult = ParserTestUtil.parse2ATDL(syntaxName, new File(TEST_FILE_DIR + syntaxName + ".peg"));
        String xmlResult = ParserTestUtil.parse2XML(syntaxName, new File(TEST_FILE_DIR + syntaxName + ".peg"));
        File atdlExpectedFile = new File(TEST_FILE_DIR + syntaxName + ".ast.atdl");
        File xmlExpectedFile = new File(TEST_FILE_DIR + syntaxName + ".ast.xml");
        //* Files.asCharSink(atdlExpectedFile, StandardCharsets.UTF_8).write(atdlResult);  // uncomment this once whenever the AST has changed
        //* Files.asCharSink(xmlExpectedFile, StandardCharsets.UTF_8).write(xmlResult);   // uncomment this once whenever the AST has changed
        assertEquals(Files.asCharSource(atdlExpectedFile, StandardCharsets.UTF_8).read(), atdlResult);
        assertEquals(Files.asCharSource(xmlExpectedFile, StandardCharsets.UTF_8).read(), xmlResult);
    }

    @Test
    public void test_ML_File_01() throws Exception {    
        String result = ParserTestUtil.parse2ATDL(syntaxName, new File(TEST_FILE_DIR + "ML_File_01.peg"));
        assertEquals("(Grammar (Production @name=\"EscapedClassChars\" (Choice (Sequence (CharClass @caption=\"-[\\\\]\" (CharRange @first=\"-\") (CharRange @first=\"[\") (CharRange @first=\"\\\\\") (CharRange @first=\"]\"))))))", result);
    }
    
    @Test
    public void test_ML_File_02() throws Exception {
        String result = ParserTestUtil.parse2ATDL(syntaxName, new File(TEST_FILE_DIR + "ML_File_02.peg"));
        assertEquals("(Grammar (Production @name=\"CodePoint\" (Choice (Sequence (NonTerminal @name=\"EscapeSequence\")) (Sequence (Predicate @type=\"MustNotMatch\" (CharClass @caption=\"\\\\-]\" (CharRange @first=\"\\\\\") (CharRange @first=\"-\") (CharRange @first=\"]\"))) (NonTerminal @name=\"DOT\")))))", result);
    }
    
    @Test
    public void test_ML_File_03() throws Exception {
        String atdlResult = ParserTestUtil.parse2ATDL(syntaxName, new File(TEST_FILE_DIR + "ML_File_03.peg"));
        assertEquals("(Grammar (Production @name=\"EscapeSequence\" (Choice (Sequence (Literal @caption=\"\\\\\") (CharClass @caption=\"btnfr\\\"'-[\\\\]\" (CharRange @first=\"b\") (CharRange @first=\"t\") (CharRange @first=\"n\") (CharRange @first=\"f\") (CharRange @first=\"r\") (CharRange @first=\"\\\"\") (CharRange @first=\"'\") (CharRange @first=\"-\") (CharRange @first=\"[\") (CharRange @first=\"\\\\\") (CharRange @first=\"]\"))) (Sequence (NonTerminal @name=\"UnicodeEscape\")))) (Production @name=\"UnicodeEscape\" (Choice (Sequence (Literal @caption=\"\\\\\") (Literal @caption=\"u\") (NonTerminal @name=\"HexQuad\")))) (Production @name=\"HexQuad\" (Choice (Sequence (NonTerminal @name=\"HexDigit\") (NonTerminal @name=\"HexDigit\") (NonTerminal @name=\"HexDigit\") (NonTerminal @name=\"HexDigit\")))) (Production @name=\"HexDigit\" (Choice (Sequence (CharClass @caption=\"0-9a-fA-F\" (CharRange @first=\"0\" @last=\"9\") (CharRange @first=\"a\" @last=\"f\") (CharRange @first=\"A\" @last=\"F\"))))) (Production @name=\"StringLiteral\" (Choice (Sequence (NonTerminal @name=\"DQ\") (Multiple @multiplicity=\"ZeroOrMore\" (Choice (Sequence (NonTerminal @name=\"EscapeSequence\")) (Sequence (Predicate @type=\"MustNotMatch\" (NonTerminal @name=\"DQ\")) (AnyChar)))) (NonTerminal @name=\"DQ\")))) (Production @name=\"EscapeSequence\" (Choice (Sequence (NonTerminal @name=\"CharEscape\")) (Sequence (NonTerminal @name=\"UnicodeEscape\")))) (Production @name=\"UnicodeEscape\" (Choice (Sequence (NonTerminal @name=\"ESC\") (Literal @caption=\"u\") (NonTerminal @name=\"HexQuad\")))) (Production @name=\"CharEscape\" (Choice (Sequence (NonTerminal @name=\"ESC\") (CharClass @caption=\"btnfr\\\"'-[\\\\]\" (CharRange @first=\"b\") (CharRange @first=\"t\") (CharRange @first=\"n\") (CharRange @first=\"f\") (CharRange @first=\"r\") (CharRange @first=\"\\\"\") (CharRange @first=\"'\") (CharRange @first=\"-\") (CharRange @first=\"[\") (CharRange @first=\"\\\\\") (CharRange @first=\"]\"))))) (Production @name=\"HexQuad\" (Choice (Sequence (NonTerminal @name=\"HexDigit\") (NonTerminal @name=\"HexDigit\") (NonTerminal @name=\"HexDigit\") (NonTerminal @name=\"HexDigit\")))) (Production @name=\"HexDigit\" (Choice (Sequence (CharClass @caption=\"0-9a-fA-F\" (CharRange @first=\"0\" @last=\"9\") (CharRange @first=\"a\" @last=\"f\") (CharRange @first=\"A\" @last=\"F\"))))) (Production @name=\"ESC\" (Choice (Sequence (Literal @caption=\"\\\\\")))) (Production @name=\"DQ\" (Choice (Sequence (Literal @caption=\"\\\"\")))))", atdlResult);
    }
    

    @Test
    public void test_ML_File_04() throws Exception {
        String result = ParserTestUtil.parse2ATDL(syntaxName, new File(TEST_FILE_DIR + "ML_File_04.peg"));
        assertEquals("(Grammar (Production @name=\"Grammar\" (Choice (Sequence (NonTerminal @name=\"w\") (Multiple @multiplicity=\"OneOrMore\" (NonTerminal @name=\"Definition\")) (NonTerminal @name=\"EOI\")))) (Production @name=\"Definition\" (Choice (Sequence (NonTerminal @name=\"Identifier\") (NonTerminal @name=\"DEFINES\") (NonTerminal @name=\"Expression\")))) (Production @name=\"Expression\" (Choice (Sequence (NonTerminal @name=\"Sequence\") (Multiple @multiplicity=\"ZeroOrMore\" (Choice (Sequence (NonTerminal @name=\"ALT\") (NonTerminal @name=\"Sequence\"))))))) (Production @name=\"Sequence\" (Choice (Sequence (Multiple @multiplicity=\"ZeroOrMore\" (NonTerminal @name=\"PrefixTerm\"))))) (Production @name=\"PrefixTerm\" (Choice (Sequence (Multiple @multiplicity=\"ZeroOrOne\" (Choice (Sequence (NonTerminal @name=\"AND\")) (Sequence (NonTerminal @name=\"NOT\")))) (NonTerminal @name=\"Term\")))) (Production @name=\"Term\" (Choice (Sequence (NonTerminal @name=\"Primary\") (Multiple @multiplicity=\"ZeroOrOne\" (Choice (Sequence (NonTerminal @name=\"OPTION\")) (Sequence (NonTerminal @name=\"STAR\")) (Sequence (NonTerminal @name=\"PLUS\"))))))))", result);
    }
    
    @Test
    public void test_Rats_PEG() throws Exception {
        String atdlResult = ParserTestUtil.parse2ATDL(syntaxName, new File(TEST_FILE_DIR + "rats.peg"));
        String xmlResult = ParserTestUtil.parse2XML(syntaxName, new File(TEST_FILE_DIR + "rats.peg"));
        File atdlExpectedFile = new File(TEST_FILE_DIR + "rats.ast.atdl");
        File xmlExpectedFile = new File(TEST_FILE_DIR + "rats.ast.xml");
        //* Files.asCharSink(atdlExpectedFile, StandardCharsets.UTF_8).write(atdlResult);  // uncomment this once whenever the AST has changed
        //* Files.asCharSink(xmlExpectedFile, StandardCharsets.UTF_8).write(xmlResult);   // uncomment this once whenever the AST has changed
        assertEquals(Files.asCharSource(atdlExpectedFile, StandardCharsets.UTF_8).read(), atdlResult);
    }
    
    @Test
    public void test_ML_String_01() throws Exception {
        String result = ParserTestUtil.parse2ATDL(syntaxName, "EscapedClassChars <- [\\-\\[\\\\\\]]");           // == ['-','[','\\',']']
        assertEquals("(Grammar (Production @name=\"EscapedClassChars\" (Choice (Sequence (CharClass @caption=\"-[\\\\]\" (CharRange @first=\"-\") (CharRange @first=\"[\") (CharRange @first=\"\\\\\") (CharRange @first=\"]\"))))))", result);
    }
    
    @Test
    public void test_ML_String_02() throws Exception {
        String result = ParserTestUtil.parse2ATDL(syntaxName, "CodePoint <- EscapeSequence / ![\\\\\\-\\]] DOT");
        assertEquals("(Grammar (Production @name=\"CodePoint\" (Choice (Sequence (NonTerminal @name=\"EscapeSequence\")) (Sequence (Predicate @type=\"MustNotMatch\" (CharClass @caption=\"\\\\-]\" (CharRange @first=\"\\\\\") (CharRange @first=\"-\") (CharRange @first=\"]\"))) (NonTerminal @name=\"DOT\")))))", result);
    }
    
    @Test
    public void test_ML_String_03() throws Exception {
        String srcString = "Space <- \"\\n\" / \"\\r\" / \"\\t\" / \"\\f\" ";
        String atdlResult = ParserTestUtil.parse2ATDL(syntaxName, srcString);
        //String xmlResult = ParserTestUtil.parse2XML(syntaxName, srcString);
        assertEquals("(Grammar (Production @name=\"Space\" (Choice (Sequence (Literal @caption=\"\\n\")) (Sequence (Literal @caption=\"\\r\")) (Sequence (Literal @caption=\"\\t\")) (Sequence (Literal @caption=\"\\f\")))))", atdlResult);
    }
    
    @Test
    public void test_ML_String_04() throws Exception {
        String result = ParserTestUtil.parse2ATDL(syntaxName, "Term <- A? B ");
        assertEquals("(Grammar (Production @name=\"Term\" (Choice (Sequence (Multiple @multiplicity=\"ZeroOrOne\" (NonTerminal @name=\"A\")) (NonTerminal @name=\"B\")))))", result);
    }
    
    @Test
    public void test_ML_String_05() throws Exception {
        String result = ParserTestUtil.parse2ATDL(syntaxName, "A <- B C D \\\n E F G");
        assertEquals("(Grammar (Production @name=\"A\" (Choice (Sequence (NonTerminal @name=\"B\") (NonTerminal @name=\"C\") (NonTerminal @name=\"D\" @an:linebreakAfter=\"true\") (NonTerminal @name=\"E\") (NonTerminal @name=\"F\") (NonTerminal @name=\"G\")))))", result);
    }
    
    @Test
    public void test_ML_String_06() throws Exception {
        Result parseResult = ParserUtil.parseString(syntaxName, "Term <-  Primary ( OPTION / STAR / PLUS )?");
        Element grammar = parseResult.semanticValue();
        Document doc = new Document(grammar);
        Document doc2 = XSLTransformerFactory.doTransform(doc, "ML-canonical");
        String atdlResult = XMLtoATDL_Serializer.write(doc2.getRootElement());
        assertEquals("(Grammar (Production @name=\"Term\" (Choice (Sequence (NonTerminal @name=\"Primary\") (Choice @epsilon=\"true\" (Sequence (NonTerminal @name=\"OPTION\")) (Sequence (NonTerminal @name=\"STAR\")) (Sequence (NonTerminal @name=\"PLUS\")))))))", atdlResult);
    }

}
