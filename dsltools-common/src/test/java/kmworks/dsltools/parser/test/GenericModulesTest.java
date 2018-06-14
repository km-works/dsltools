package kmworks.dsltools.parser.test;

import kmworks.dsltools.ast.base.*;
import kmworks.dsltools.parser.base.*;
import static kmworks.dsltools.parser.base.CodepointUtils.*;
import static org.junit.Assert.*;

import org.junit.Test;
import xtc.parser.Result;

import java.io.Reader;
import java.io.StringReader;
import java.util.Map;

public class GenericModulesTest {

//<editor-fold defaultstate="collapsed" desc="Private helpers">

    //TODO: move these helper methods to ParserTestUtil !

    private void verify(String input, String expected) {
        String result = null;
        try {
            result = parse(input);
            assertEquals(expected, result);
        } catch (Exception ex) {
            result = ex.getMessage();
        }
        printResult(input, result);
    }

    private void falsify(String input, String errorMsg) {
        String result =  null;
        try {
            result = parse(input);
        } catch (Exception ex) {
            result = ex.getMessage();
        }
        printResult(input, result);
        assertTrue(result.contains(errorMsg));
    }

    private String parse(String input) throws Exception {
        return parse(input, null);
    }

    private String parse(String input, Map<String, Predefined.SyntaxType> syntaxTypes) throws Exception {
        Result parseResult;
        {
            Reader reader = new StringReader(input);
            AbstractParser parser = new GenericModulesTest_Parser(reader, null, input.length());
            if (syntaxTypes != null) {
                for (String key : syntaxTypes.keySet()) {
                    parser.setSyntaxType(key, syntaxTypes.get(key));
                }
            }
            parseResult = parser.parse();
            reader.close();
            if (parseResult == null) {
                throw new RuntimeException("Parser returned no result");
            }
        }
        if (parseResult.hasValue()) {
            StringLiteralNode node = parseResult.semanticValue();
            return toCharSeq(node.parsedString());
        } else {
            throw new RuntimeException(AbstractParser.fmtErrorMsg(input, parseResult.parseError()));
        }
    }

    private String toCharSeq(String s) {
        StringBuilder sb = new StringBuilder();
        for (char ch : s.toCharArray()) {
            sb.append(' ').append(String.format("%02x", (int) ch));
        }
        return "[" + sb.toString().substring(1) + "]";
    }

    private void printResult(String input, String result) {
        pln(input + " -> " + result);
    }

    private void header(String h) {
        pln("\nTEST: " + h);
    }
    private void pln(String s) {
        System.out.println(s);
    }
//</editor-fold>

    @Test   // DEFAULT syntax StringLiteral with all escaped chars: \r (0x0d), \n (0x0a), \" (0x22), \\ (0x5c)
    public void testGenericStringLiteral_01() {
        header("GenericStringLiteral_01");
        verify("\"\\r\\n\\\"\\\\\"", "[0d 0a 22 5c]");
    }

    @Test   // DEFAULT syntax StringLiteral with Unicode escape sequences
    public void testGenericStringLiteral_02()  {
        header("GenericStringLiteral_02");
        verify("\"\\u007f\\u0020\\u0000\\u2000\"", "[7f 20 00 2000]");
    }

    @Test   // DEFAULT syntax StringLiteral with invalid Unicode escape sequence
    public void testGenericStringLiteral_02b() {
        header("GenericStringLiteral_02b");
        falsify("\"\\u7f\"", "hex digit expected");
    }
}
