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
package kmworks.dsltools.rrd;

import static com.google.common.base.Preconditions.*;
import java.net.URISyntaxException;
import javax.annotation.Nonnull;
import kmworks.dsltools.adt.ml.XMLNodeWalkerImpl;
import kmworks.dsltools.adt.ml.XMLTreeVisitor;
import kmworks.dsltools.util.xml.Namespaces;
import nu.xom.Attribute;
import nu.xom.Element;

/**
 *
 * @author Christian P. Lerch
 */
public final class XMLtoRRD_Transformer extends XMLTreeVisitor<ElementNode> {
    
    private static final String MSG_NON_NULL = "%s must not be null";
    private static final String MSG_NODE_NON_NULL = String.format(MSG_NON_NULL, "Node");
     private static final String MSG_WRONG_NODE_TYPE = "Wrong node type";
    
    private ElementNode result;
    private final XMLNodeWalkerImpl walker;

    public XMLtoRRD_Transformer() {
        super();
        walker = new XMLNodeWalkerImpl(this);
    }

    @Override
    public ElementNode transform(Element node) {
        if (!(node.getLocalName().equals("Grammar"))) {
            throw new IllegalArgumentException("node must be of type Grammar");
        }
        walker.walkGrammar(node);
        assert stack().isEmpty();
        return result;
    }

    @Override
    public ElementNode newFor(String name) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public ElementNode newFrom(Element node) {
        throw new UnsupportedOperationException();
    }
    
    /*
     * Grammar :: Production+
     */

    @Override
    public void visitGrammar(Element node) {
        assert node.getLocalName().equals("Grammar");
        RRGrammar that = new RRGrammar();
        copyAnnotations(node, that);
        result = that;     // capture the transformer result here
        push(that);
    }

    /*
     * Production :: Name Choice
     */

    @Override
    public void visitProduction(Element node) {
        assert node.getLocalName().equals("Production");
        RRGrammar top = (RRGrammar)top();
        RRProduction that = new RRProduction(new RRLabel(node.getAttributeValue("name")));
        copyAnnotations(node, that);
        top.addChild(that);
        push(that);
    }

    /*
     * Choice :: epsilon:Boolean Sequence+
     */

    @Override
    public void visitChoice(Element node) {
        assert node.getLocalName().equals("Choice");
        ElementNode top = top();
        RRChoice that = new RRChoice();
        if (Boolean.valueOf(node.getAttributeValue("epsilon"))) {
            that.addChild(new RREpsilon());
        }
        copyAnnotations(node, that);
        top.addChild(that);
        push(that);
    }

    /*
     * Sequence :: Term+
     */

    @Override
    public void visitSequence(Element node) {
        assert node.getLocalName().equals("Sequence");
        ElementNode top = top();
        RRSequence that = new RRSequence();
        if (isTopLevelSequence(node)) {
            that.setTopLevel();
        }
        copyAnnotations(node, that);
        top.addChild(that);
        push(that);
    }

    /*
     * Predicate :: type:PredicateType Predicable
     */
    @Override
    public void visitPredicate(Element node) {
        assert node.getLocalName().equals("Predicate");
        ElementNode top = top();
        RRPredicate that = new RRPredicate(node.getAttributeValue("type"));
        copyAnnotations(node, that);
        top.addChild(that);
        push(that);
    }
    
    /*
     *  Multiple :: multiplicity:Multiplicity Primary
     */
    @Override
    public void visitMultiple(Element node) {
        assert node.getLocalName().equals("Multiple");
        ElementNode top = top();
        SingletonNode that;
        String multi = node.getAttributeValue("multiplicity");
        switch (multi) {
            case "ZeroOrOne":
                that = new RRZeroOrOne();
                break;
            case "ZeroOrMore":
                that = new RRZeroOrMore();
                break;
            case "OneOrMore":
                that = new RROneOrMore();
                break;
            default:
                throw new IllegalArgumentException("Illegal multiplicitiy: " + multi);
        }
        copyAnnotations(node, that);
        top.addChild(that);
        push(that);
    }
    
    /*
     *  NonTerminal :: name:String
     */
    @Override
    public void visitNonTerminal(@Nonnull Element node) {
        checkNotNull(node, MSG_NODE_NON_NULL);
        checkArgument(node.getLocalName().equals("NonTerminal"), MSG_WRONG_NODE_TYPE);
        final ElementNode top = top();
        final RRNonTerminal that;
        try {
            that = new RRNonTerminal(node.getAttributeValue("name"));
            copyAnnotations(node, that);
            top.addChild(that);
        } catch (URISyntaxException ex) {
            /* CHECKED BY PARSER; CANNOT HAPPEN */
        }
    }

    @Override
    public void visitAnyChar(Element node) {
        assert node.getLocalName().equals("AnyChar");   // not to be confused with RRLiteral
        ElementNode top = top();
        RRTerminal that = new RRTerminal("<ANY>");
        copyAnnotations(node, that);
        top.addChild(that);
    }

    /*
     *  Literal :: caption:String
     */
    @Override
    public void visitLiteral(Element node) {
        assert node.getLocalName().equals("Literal");   // not to be confused with RRLiteral
        ElementNode top = top();
        RRTerminal that = new RRTerminal(node.getAttributeValue("caption"));
        copyAnnotations(node, that);
        top.addChild(that);
    }

    /*
     *  CharClass :: value:String caption:String CharRange+
     */
    @Override
    public void visitCharClass(Element node) {
        assert node.getLocalName().equals("CharClass");   // not to be confused with RRLiteral
        ElementNode top = top();
        RRTerminal that = new RRTerminal(node.getAttributeValue("caption"));
        copyAnnotations(node, that);
        top.addChild(that);
    }

    // Nothing to pop for a leaf node; thus we must override the super method
    
    @Override
    public void leaveNonTerminal(Element node) {}

    @Override
    public void leaveAnyChar(Element node) {}

    @Override
    public void leaveLiteral(Element node) {}

    @Override
    public void leaveCharClass(Element node) {}

    // Nothing to push or pop for these ignored nodes
    
    @Override
    public void visitCharRange(Element node) {}

    @Override
    public void leaveCharRange(Element node) {}
    
    
    private boolean isTopLevelSequence(Element node) {
        return ((Element)node.getParent().getParent()).getLocalName().equals("Production");
    }

    private void copyAnnotations(Element fromNode, ElementNode toNode) {
        checkNotNull(fromNode);
        checkNotNull(toNode);
        Attribute attr;
        attr = fromNode.getAttribute("linebreakAfter", Namespaces.AN_URI);
        if (attr != null) {
            toNode.setAttribute("linebreakAfter", attr.getValue());
        }
    }

}
