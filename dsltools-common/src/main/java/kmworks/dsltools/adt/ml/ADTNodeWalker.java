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
package kmworks.dsltools.adt.ml;

/** An interface for walking an ADT tree.
 *
 * @author Christian P. Lerch
 */
public interface ADTNodeWalker {
    
    void walkGrammar(Grammar node);

    void walkProduction(Production node);

    void walkSequence(Sequence node);

    void walkTerm(Term node);
    
    void walkPredicate(Predicate node);
    
    void walkPredicable(Predicable node);

    void walkMultiple(Multiple node);

    void walkPrimary(Primary node);
    
    void walkChoice(Choice node);

    void walkNonTerminal(NonTerminal node);

    void walkTerminal(Terminal node);
    
    void walkAnyChar(AnyChar node);

    void walkLiteral(Literal node);

    void walkCharClass(CharClass node);

    void walkCharRange(CharRange node);

 }
