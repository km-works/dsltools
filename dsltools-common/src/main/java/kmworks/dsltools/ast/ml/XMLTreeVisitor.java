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
package kmworks.dsltools.ast.ml;

import javax.annotation.Nonnull;
import kmworks.dsltools.ast.base.DepthFirstVisitor;
import kmworks.dsltools.ast.base.TreeTransformer;
import kmworks.util.ds.PeekingArrayListStack;
import kmworks.util.ds.PeekingStack;
import nu.xom.Element;

/**
 *
 * @author Christian P. Lerch
 * @param <T>   target tree node type
 */
public abstract class XMLTreeVisitor<T> 
        implements DepthFirstVisitor<T>, GenericNodeVisitor<Element>, TreeTransformer<Element, T> {

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
    public abstract T newFrom(@Nonnull Element node);

    /** Construct a new target node from the given source node and push it onto the tree builder stack.
     * 
     * @param node The source node
     */
    public void pushFrom(@Nonnull Element node) {
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
    public void visitGrammar(Element node) {
        pushFrom(node);
    }

    @Override
    public void leaveGrammar(Element node) {
        pop();
    }

    @Override
    public void visitProduction(Element node) {
        pushFrom(node);
    }

    @Override
    public void leaveProduction(Element node) {
        pop();
    }

    @Override
    public void visitSequence(Element node) {
        pushFrom(node);
    }

    @Override
    public void leaveSequence(Element node) {
        pop();
    }

    @Override
    public void visitPredicate(Element node) {
        pushFrom(node);
    }

    @Override
    public void leavePredicate(Element node) {
        pop();
    }

    @Override
    public void visitMultiple(Element node) {
        pushFrom(node);
    }

    @Override
    public void leaveMultiple(Element node) {
        pop();
    }

    @Override
    public void visitNonTerminal(Element node) {
        pushFrom(node);
    }

    @Override
    public void leaveNonTerminal(Element node) {
        pop();
    }

    @Override
    public void visitLiteral(Element node) {
        pushFrom(node);
    }

    @Override
    public void leaveLiteral(Element node) {
        pop();
    }

    @Override
    public void visitCharClass(Element node) {
        pushFrom(node);
    }

    @Override
    public void leaveCharClass(Element node) {
        pop();
    }

    @Override
    public void visitCharRange(Element node) {
        pushFrom(node);
    }

    @Override
    public void leaveCharRange(Element node) {
        pop();
    }

    @Override
    public void visitAnyChar(Element node) {
        pushFrom(node);
    }

    @Override
    public void leaveAnyChar(Element node) {
        pop();
    }

    @Override
    public void visitChoice(Element node) {
        pushFrom(node);
    }

    @Override
    public void leaveChoice(Element node) {
        pop();
    }

}
