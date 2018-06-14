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

import kmworks.dsltools.ast.base.ASTNode;
import nu.xom.Attribute;
import nu.xom.Document;
import nu.xom.Element;

/**
 *
 * @author Christian P. Lerch
 */
public final class ADTtoXML_Transformer extends ADTTreeVisitor<Element> {

    private final Document doc;
    private final ADTNodeWalkerImpl walker;
    
    public ADTtoXML_Transformer() {
        super();
        walker = new ADTNodeWalkerImpl(this);
        doc = new Document(newFor("Grammar"));
    }

    @Override
    public Element transform(ASTNode node) {
        if (!(node instanceof Grammar)) {
            throw new IllegalArgumentException("node must be of type Grammar");
        }
        walker.walkGrammar((Grammar)node);
        assert stack().isEmpty();
        return doc.getRootElement();
    }

    @Override
    public Element newFor(String name) {
        return new Element(name);
    }
    
    @Override
    public Element newFrom(ASTNode node) {
        String name = node.getClass().getSimpleName();
        if (name.endsWith("Impl")) {
            name = name.substring(0, name.length() - 4);
        }
        return newFor(name);
    }
    
    /*
        Transform Grammar elements
     */

    @Override
    public void visitGrammar(Grammar node) {
        super.visitGrammar(node);
    }

    @Override
    public void visitProduction(Production node) {
        super.visitProduction(node);
        top().addAttribute(mkAttr("name", node.name()));
    }
    
    @Override
    public void visitChoice(Choice node) {
        super.visitChoice(node);
        if (node.epsilon()) {
            top().addAttribute(mkAttr("epsilon", node.epsilon()));
        }
    }

    @Override
    public void visitSequence(Sequence node) {
        super.visitSequence(node);
        /*
        if (!node..annotations().isEmpty()) {
            top().addAttribute(mkAttr("annotation", node.annotations().contents()));
        }
        */
    }
    
    @Override
    public void visitPredicate(Predicate node) {
        super.visitPredicate(node);
        top().addAttribute(mkAttr("type", node.type().name()));
    }
    
    @Override
    public void visitMultiple(Multiple node) {
        super.visitMultiple(node);
        top().addAttribute(mkAttr("multiplicity", node.multiplicity().name()));
    }

    @Override
    public void visitNonTerminal(NonTerminal node) {
        super.visitNonTerminal(node);
        top().addAttribute(mkAttr("name", node.name()));
    }

    @Override
    public void visitLiteral(Literal node) {
        super.visitLiteral(node);
        top().addAttribute(mkAttr("caption", node.caption()));
    }

    @Override
    public void visitCharClass(CharClass node) {
        super.visitCharClass(node);
        top().addAttribute(mkAttr("value", node.value()));
        top().addAttribute(mkAttr("caption", node.caption()));
    }

    @Override
    public void visitCharRange(CharRange node) {
        super.visitCharRange(node);
        top().addAttribute(mkAttr("first", node.first()));
        if (node.last() != null)
            top().addAttribute(mkAttr("last", node.last()));
    }

    private Attribute mkAttr(String name, Object o) {
        return new Attribute(name, o.toString());
    }

}
