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

import javax.annotation.Nonnull;
import kmworks.dsltools.adt.base.ADTNode;
import kmworks.dsltools.adt.base.DepthFirstVisitor;
import kmworks.dsltools.adt.base.TreeTransformer;
import kmworks.util.ds.PeekingArrayListStack;
import kmworks.util.ds.PeekingStack;

/**
 *
 * @author Christian P. Lerch
 * @param <T>   target tree node type
 */
public abstract class ADTTreeVisitor<T> 
        implements DepthFirstVisitor<T>, ADTNodeVisitor, TreeTransformer<ADTNode, T> {

    /*
     *   Tree builder stack
     */
    private final PeekingStack<T> stack = new PeekingArrayListStack<>(256);

    /** Create a new target node for a name given as a String.
     * 
     * @param name The target node name
     * @return 
     */
    public abstract T newFor(@Nonnull String name);
    
    /** Construct a new target node from a source node.
     * 
     * @param node The source node
     * @return New target node constructed from the source node
     */
    public abstract T newFrom(@Nonnull ADTNode node);

    /** Construct a new target node from the given source node and push it onto the tree builder stack.
     * 
     * @param node The source node
     */
    public void pushFrom(@Nonnull ADTNode node) {
        stack().push(newFrom(node));
    }

    /**
     * 
     * @return The tree builder stack
     */
    @Override
    public PeekingStack<T> stack() {
        return stack;
    }
    
    /*
     *  Default tree building visitor methods
     */

    @Override
    public void visitGrammar(Grammar node) {
        pushFrom(node);
    }

    @Override
    public void leaveGrammar(Grammar node) {
        pop();
    }

    @Override
    public void visitProduction(Production node) {
        pushFrom(node);
    }

    @Override
    public void leaveProduction(Production node) {
        pop();
    }

    @Override
    public void visitSequence(Sequence node) {
        pushFrom(node);
    }

    @Override
    public void leaveSequence(Sequence node) {
        pop();
    }

    @Override
    public void visitPredicate(Predicate node) {
        pushFrom(node);
    }

    @Override
    public void leavePredicate(Predicate node) {
        pop();
    }

    @Override
    public void visitMultiple(Multiple node) {
        pushFrom(node);
    }

    @Override
    public void leaveMultiple(Multiple node) {
        pop();
    }

    @Override
    public void visitChoice(Choice node) {
        pushFrom(node);
    }

    @Override
    public void leaveChoice(Choice node) {
        pop();
    }

    @Override
    public void visitNonTerminal(NonTerminal node) {
        pushFrom(node);
    }

    @Override
    public void leaveNonTerminal(NonTerminal node) {
        pop();
    }

    @Override
    public void visitAnyChar(AnyChar node) {
        pushFrom(node);
    }

    @Override
    public void leaveAnyChar(AnyChar node) {
        pop();
    }

    @Override
    public void visitLiteral(Literal node) {
        pushFrom(node);
    }

    @Override
    public void leaveLiteral(Literal node) {
        pop();
    }

    @Override
    public void visitCharClass(CharClass node) {
        pushFrom(node);
    }

    @Override
    public void leaveCharClass(CharClass node) {
        pop();
    }

    @Override
    public void visitCharRange(CharRange node) {
        pushFrom(node);
    }

    @Override
    public void leaveCharRange(CharRange node) {
        pop();
    }

}
