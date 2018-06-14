package kmworks.dsltools.parser.adt;

import kmworks.dsltools.parser.base.AbstractParser;
import kmworks.dsltools.parser.base.ParserFactory;
import org.junit.Test;

import java.io.Reader;
import java.io.StringReader;

import static org.junit.Assert.assertEquals;

public class ADT_ParserTest {

    @Test
    public void testParserInit_01() throws Exception {
        Reader srcReader = new StringReader("");
        AbstractParser parser = ParserFactory.newParser("ADT", srcReader, 0);
        assertEquals("JAVA", parser.syntaxTypeFor(AbstractParser.IDENTIFIER$SYNTAX_MODULE));
        assertEquals("DEFAULT", parser.syntaxTypeFor(AbstractParser.STRING_LITERAL$SYNTAX_MODULE));
        assertEquals("DEFAULT", parser.syntaxTypeFor(AbstractParser.NUMERALS$SYNTAX_MODULE));
        assertEquals("DEFAULT", parser.syntaxTypeFor(AbstractParser.WHITESPACE$SYNTAX_MODULE));
    }
}
