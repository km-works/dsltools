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

/**
 *
 * @author Christian P. Lerch
 */
public interface ADTNodeVisitor {

    void visitGrammar(Grammar node);
    void leaveGrammar(Grammar node);

    void visitProduction(Production node);
    void leaveProduction(Production node);

    void visitSequence(Sequence node);
    void leaveSequence(Sequence node);

    void visitPredicate(Predicate node);
    void leavePredicate(Predicate node);

    void visitMultiple(Multiple node);
    void leaveMultiple(Multiple node);

    void visitChoice(Choice node);
    void leaveChoice(Choice node);
    
    void visitNonTerminal(NonTerminal node);
    void leaveNonTerminal(NonTerminal node);

    void visitAnyChar(AnyChar node);
    void leaveAnyChar(AnyChar node);

    void visitLiteral(Literal node);
    void leaveLiteral(Literal node);

    void visitCharClass(CharClass node);
    void leaveCharClass(CharClass node);

    void visitCharRange(CharRange node);
    void leaveCharRange(CharRange node);

}
