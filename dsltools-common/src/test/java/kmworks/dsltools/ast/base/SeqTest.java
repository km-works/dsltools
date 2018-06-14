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
package kmworks.dsltools.ast.base;

import org.junit.Test;
import static org.junit.Assert.*;
import xtc.util.Pair;

/**
 *
 * @author Christian P. Lerch
 */
public class SeqTest {
    
    public SeqTest() {
    }

    @Test
    public void testOf_0args() {
        Seq<String> seq = Seq.of();
        assertTrue(seq.list().isEmpty());
    }

    @Test
    public void testOf_GenericType_1() {
        Seq<String> seq = Seq.of("1");
        assertTrue(seq.list().size() == 1);
        assertEquals("1", seq.list().get(0));
    }

    @Test
    public void testOf_GenericType_2() {
        Seq<String> seq = Seq.of("1", "2");
        assertTrue(seq.list().size() == 2);
        assertEquals("1", seq.list().get(0));
        assertEquals("2", seq.list().get(1));
    }

    @Test
    public void testOf_Pair_0() {
        Seq<String> seq = Seq.of(Pair.EMPTY);
        assertTrue(seq.list().isEmpty());
    }

    @Test
    public void testOf_Pair_1() {
        Seq<String> seq = Seq.of(new Pair("1"));
        assertTrue(seq.list().size() == 1);
        assertEquals("1", seq.list().get(0));
    }

    @Test
    public void testOf_Pair_HT_1() {
        Seq<String> seq = Seq.of("1", Pair.EMPTY);
        assertTrue(seq.list().size() == 1);
        assertEquals("1", seq.list().get(0));
    }

    @Test
    public void testOf_Pair_HT_2() {
        Seq<String> seq = Seq.of("1", new Pair("2"));
        assertTrue(seq.list().size() == 2);
        assertEquals("1", seq.list().get(0));
        assertEquals("2", seq.list().get(1));
    }

    @Test
    public void testOf_Pair_null() {
        Seq<String> seq = Seq.of((Pair)null);
        assertTrue(seq.list().isEmpty());
    }

    @Test(expected = NullPointerException.class)
    public void testOf_vargs_null() {
        Seq<String> seq = Seq.of((String)null);
        assertTrue(seq.list().isEmpty());
    }

}
