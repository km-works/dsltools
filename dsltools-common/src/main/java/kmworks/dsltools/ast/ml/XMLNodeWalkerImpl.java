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

import nu.xom.Element;

/**
 * Created by cpl on 18.03.2017.
 */
public final class XMLNodeWalkerImpl implements GenericNodeWalker<Element> {

    private final GenericNodeVisitor<Element> visitor;

    public XMLNodeWalkerImpl(GenericNodeVisitor<Element> visitor) {
        this.visitor = visitor;
    }

    @Override
    public void walkGrammar(Element node) {
        visitor.visitGrammar(node);     // skip annotation nodes:
        node.getChildElements("Production").list().forEach(n -> walkProduction(n));
        visitor.leaveGrammar(node);
    }

    @Override
    public void walkProduction(Element node) {
        visitor.visitProduction(node);  // skip annotation nodes:
        walkChoice(node.getFirstChildElement("Choice"));    // there is only one Choice
        visitor.leaveProduction(node);
    }

    @Override
    public void walkSequence(Element node) {
        visitor.visitSequence(node);
        for (Element n : node.getChildElements().list()) {
            if (!n.getLocalName().equals("annotation")) { // skip annotation nodes
                walkTerm(n);
            }
        }
        visitor.leaveSequence(node);
    }

    @Override
    public void walkPredicate(Element node) {
        visitor.visitPredicate(node);
        for (Element n : node.getChildElements().list()) {
            if (!n.getLocalName().equals("annotation")) { // skip annotation nodes
                walkPredicable(n);      // there is only one Predicable
            }
        }
        visitor.leavePredicate(node);
    }

    @Override
    public void walkMultiple(Element node) {
        visitor.visitMultiple(node);
        for (Element n : node.getChildElements().list()) {
            if (!n.getLocalName().equals("annotation")) { // skip annotation nodes
                walkPrimary(n);      // there is only one Primary
            }
        }
        visitor.leaveMultiple(node);
    }

    @Override
    public void walkChoice(Element node) {
        visitor.visitChoice(node);  // skip annotation nodes:
        node.getChildElements("Sequence").list().forEach(n -> walkSequence(n));
        visitor.leaveChoice(node);
    }

    @Override
    public void walkNonTerminal(Element node) {
        visitor.visitNonTerminal(node);
        visitor.leaveNonTerminal(node);
    }

    @Override
    public void walkAnyChar(Element node) {
        visitor.visitAnyChar(node);
        visitor.leaveAnyChar(node);
    }

    @Override
    public void walkLiteral(Element node) {
        visitor.visitLiteral(node);
        visitor.leaveLiteral(node);
    }

    @Override
    public void walkCharClass(Element node) {
        visitor.visitCharClass(node);   // skip annotation nodes:
        node.getChildElements("CharRange").list().forEach(n -> walkCharRange(n));
        visitor.leaveCharClass(node);
    }

    @Override
    public void walkCharRange(Element node) {
        visitor.visitCharRange(node);
        visitor.leaveCharRange(node);
    }

    @Override
    public void walkTerm(Element node) {
        switch (node.getLocalName()) {
            case "Predicate":
                walkPredicate(node);
                break;
            default:    // Predicable = Multiple | Primary
                walkPredicable(node);
                break;
        }
    }
    
    @Override
    public void walkPredicable(Element node) {
        switch (node.getLocalName()) {
            case "Multiple":
                walkMultiple(node);
                break;
            default:    // Primary = Choice | NonTerminal | Terminal
                walkPrimary(node);
                break;
        }
    }

    @Override
    public void walkPrimary(Element node) {
        switch (node.getLocalName()) {
            case "NonTerminal":
                walkNonTerminal(node);
                break;
            case "Choice":
                walkChoice(node);
                break;
            default:    // Terminal = Literal | CharClass | AnyCahr
                walkTerminal(node);
                break;
        }
    }
    
    @Override
    public void walkTerminal(Element node) {
        switch (node.getLocalName()) {
            case "AnyChar":
                walkAnyChar(node);
                break;
            case "Literal":
                walkLiteral(node);
                break;
            case "CharClass":
                walkCharClass(node);
                break;
            default:
                throw new IllegalStateException("Illegal node type: " + node.getLocalName());
        }
    }

}
