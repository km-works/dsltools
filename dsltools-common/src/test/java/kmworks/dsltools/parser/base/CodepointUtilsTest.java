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

import kmworks.util.ds.rng.IntPredicate;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 * @author Christian P. Lerch
 */
public class CodepointUtilsTest {
    
    /**
     * test JAVA$INVALID_STRING_LITERAL_CHARS
     */
    @Test
    public void testJavaStringLiteralInvalidChars_01() {
        IntPredicate cp = CodepointUtils.Predefined.JAVA$STRING_LITERAL_INVALID_CHARS$SET.get();

        assertFalse(cp.contains(0));
        assertFalse(cp.contains('\f'));
        assertTrue(cp.contains('\r'));
        assertTrue(cp.contains('\n'));
        assertTrue(cp.contains('\\'));
        assertTrue(cp.contains('\"'));
        assertFalse(cp.contains('\u201c'));
        assertFalse(cp.contains(' '));
    }

    @Test
    public void testJavaStringLiteralMandatoryEscapeChars() {

        // This WONT compile, because Unicode escapes are pre-processed before compilation,
        // and this would effectively insert a LF into an unparsed string literal, which is illegal
        // in line comments (try to replace the double-RS with a single-RS!), which may be a compiler bug.
        // String test_LF_invalid = "\\u000a";
        // Strangely in block comments this is handled correctly:
        /* String test_LF_invalid1 = "\u000a";  */
        // This WONT compile either, because THIS octal escape sequence is also pre-processed before compilation,
        // and would effectively insert a LF into an unparsed string literal, which is illegal.
        // String test_LF_invalid2 = "\012";
        // This WILL compile, because escaped chars are not pre-processed before compilation, and
        // the Java compiler knows how to handle it correctly. It's the only correct way to represent a LF in a
        // Java String.
        String test_LF_valid = "\n";

        // This WONT compile, because Unicode escapes are pre-processed before compilation,
        // and this would effectively insert a CR into an unparsed string literal, which is illegal,
        // even in line comments (try to replace the double-RS with a single-RS!), which may be a compiler bug.
        // String test_CR_invalid = "\\u000d";
        // Strangely in block comments this is handled correctly:
        /* String test_CR_invalid1 = "\u000d";  */
        // This WONT compile either, because THIS octal escape sequence is also pre-processed before compilation,
        // and would effectively insert a LF into an unparsed string literal, which is illegal.
        /* String test_CR_invalid2 = "\015"  */
        // This WILL compile, because escaped chars are not pre-processed before compilation, and
        // the Java compiler knows how to handle it correctly. It's the only correct way to represent a CR in a
        // Java String.
        String test_CR_valid = "\r";

        // This WONT compile, because Unicode escapes are pre-processed before compilation,
        // and would effectively insert a DQ into an unparsed string literal, which is illegal.
        // (ERROR: Unclosed string literal)
        // String test_DQ_invalid = "\u0022";
        // Strangely, this WILL compile, because THIS octal escape sequence is NOT pre-processed before
        // compilation, and the Java compiler knows how to handle it correctly.
        String test_DQ_valid1 = "\042";
        // This WILL compile, because escaped chars are not pre-processed before compilation, and
        // the Java compiler knows how to tread them correctly.
        String test_DQ_valid2 = "\"";
        assertEquals(test_DQ_valid1, test_DQ_valid2);

        // This WONT compile, because Unicode escapes are pre-processed before compilation,
        // and would effectively insert a RS into an unparsed string literal, which is illegal.
        // String test_RS_invalid = "\u005c";
        // Strangely, this WILL compile, because THIS octal escape sequence is NOT pre-processed before
        // compilation, and the Java compiler knows how to handle it correctly.
        String test_RS_valid1 = "\134";
        // This WILL compile, because escaped chars are not pre-processed before compilation, and
        // the Java compiler knows how to tread them correctly.
        String test_RS_valid2 = "\\";
        assertEquals(test_RS_valid1, test_RS_valid2);
    }

    @Test
    public void testJavaStringLiteralOptionalEscapeChars() {

        // This WILL compile, because inserting a NULL into an unparsed string literal is perfectly legal.
        String test_NULL_valid1 = "\u0000";
        // This WILL also compile, its just an alternative syntax for the same character value.
        String test_NULL_valid2 = "\0";
        assertEquals(test_NULL_valid1, test_NULL_valid2);
        assertEquals(0, test_NULL_valid1.charAt(0));

        // This WILL compile, because inserting a BS into an unparsed string literal is perfectly legal.
        String test_BS_valid1 = "\u0008";
        // This WILL also compile, its just an alternative syntax for the same character value.
        String test_BS_valid2 = "\b";
        assertEquals(test_BS_valid1, test_BS_valid2);
        assertEquals(0x08, test_BS_valid1.charAt(0));

        // This WILL compile, because inserting a HT into an unparsed string literal is perfectly legal.
        String test_HT_valid1 = "\u0009";
        // This WILL also compile, its just an alternative syntax for the same character value.
        String test_HT_valid2 = "\t";
        assertEquals(test_HT_valid1, test_HT_valid2);
        assertEquals(0x09, test_HT_valid1.charAt(0));

        // This WILL compile, because inserting a FF into an unparsed string literal is perfectly legal.
        String test_FF_valid1 = "\u000c";
        // This WILL also compile, its just an alternative syntax for the same character value.
        String test_FF_valid2 = "\f";
        assertEquals(test_FF_valid1, test_FF_valid2);
        assertEquals(0x0c, test_FF_valid1.charAt(0));

        // This WILL compile, because inserting a SQ into an unparsed string literal is perfectly legal.
        String test_SQ_valid1 = "\u0027";
        // This WILL also compile, its just an alternative syntax for the same character value.
        String test_SQ_valid2 = "\'";
        // In this case also the plain literal character SQ will work - unclear, why an escaped char even exists.
        String test_SQ_valid3 = "'";
        assertEquals(test_SQ_valid1, test_SQ_valid2);
        assertEquals(test_SQ_valid1, test_SQ_valid3);
        assertEquals(0x27, test_SQ_valid1.charAt(0));

    }

    @Test
    public void testJavaStringLiteralOctalEscapes() {

    }

    /**
     * Test Java string literals escaped chars
     */
    @Test
    public void testJavaEscapedChars_01() {

    }
}
