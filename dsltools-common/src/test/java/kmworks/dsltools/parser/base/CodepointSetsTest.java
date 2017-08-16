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

import kmworks.util.cp.CodepointPredicate;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Christian P. Lerch
 */
public class CodepointSetsTest {
    
    public CodepointSetsTest() {
    }

    /**
     * CodepointSets.DEFAULT_INVALID_STRING_CHAR_SET contains all control characters less U+0020
     */
    @Test
    public void testISC_ControlChars() {
        CodepointPredicate cp = CodepointSets.DEFAULT$INVALID_STRING_CHAR_SET;
        for (int i=0; i<0x20; i++) {
            assertTrue(cp.contains(i));
        }
        assertTrue(cp.contains(0));
        assertTrue(cp.contains('\f'));
        assertTrue(cp.contains('\\'));
        assertFalse(cp.contains(' '));
    }

}
