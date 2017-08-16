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

import com.google.common.collect.Lists;
import java.awt.Point;
import java.net.URI;
import java.util.List;
import static kmworks.dsltools.rrd.ElementNode.*;

/**
 *
 * @author Christian P. Lerch
 * @version 1.0.0
 * @since 1.0
 */
public final class SVGRenderingVisitor {

    private final SVGRenderer r;

    public SVGRenderingVisitor(SVGRenderer renderer) {
        r = renderer;
    }

    public void renderGrammar(RRGrammar that, RenderingParams params) {
        for (ElementNode child : that.getChildren()) {
            child.accept(this, params);
        }
    }

    public void renderProduction(RRProduction that, RenderingParams params) {
        final Point origin = params.getOrigin();
        // render label and first choice
        final RRLabel label = that.getLabel();
        r.beginSVGElement(that.getWidth() + origin.x + 10, that.getHeight() + origin.y, label.getLabel());
        final Point loc = move(new Point(0, label.getTextHeight() + that.tPad), origin);
        r.drawAnchoredText(loc.x, loc.y, label.getLabel());
        renderChoice((RRChoice) that.getChild(), params.copyOf().setOrigin(move(loc, new Point(that.hPad, -8))));
    }

    public void renderChoice(RRChoice that, RenderingParams params) {
        final Point origin = params.getOrigin();
        final int size = that.getSize();
        assert size > 0;
        final int hPad = size > 1 ? HPAD : 0;
        if (size == 1) {
            that.getFirstChild().accept(this, params);
            return;
        }
        assert size > 1;
        final Point entry = move(that.getEnter(), origin);     // entry point of Choice structure
        final Point exit = move(that.getExit(), origin);       // exit point of Choice structure
        Point chOrigin = move(origin, that.moveHorMargin());
        Point chEntry = new Point();
        Point chExit = new Point();
        // vertical connector lines x-coordinates
        final int leftX = entry.x + hPad + RAD;
        final int rightX = exit.x - hPad - RAD;

        for (int i = 0; i < size; i++) {
            final ElementNode ch = that.getChildren().get(i);
            // draw child[i]  0<=i<size
            ch.accept(this, params.copyOf().setOrigin(chOrigin));
            // current child entry and exit points
            chEntry = move(ch.getEnter(), chOrigin);
            chExit = move(ch.getExit(), chOrigin);
            // draw trailer line
            r.drawLine(chExit.x, chExit.y, rightX - RAD, chExit.y);
            if (i > 0) {
                // draw child-level arc connectors
                r.quaterArc(leftX + RAD, chEntry.y - RAD, RAD, 3);
                r.quaterArc(rightX - RAD, chExit.y - RAD, RAD, 4);
            }
            // shift origin to next slot
            chOrigin = move(chOrigin, ch.nextVerOrigin());
        }
        // draw left and right vertical connector lines
        r.drawLine(leftX, entry.y + RAD, leftX, chEntry.y - RAD);
        r.drawLine(rightX, exit.y + RAD, rightX, chExit.y - RAD);
        // draw left and right hoizontal connectors
        r.drawLine(entry.x, entry.y, entry.x + that.horMargin(), entry.y);
        r.drawLine(exit.x - that.horMargin(), exit.y, exit.x, exit.y);
        // draw element-level arc connectors
        r.quaterArc(entry.x + hPad, entry.y + RAD, RAD, 1);
        r.quaterArc(exit.x - hPad, exit.y + RAD, RAD, 2);
    }

    public void renderEpsilon(RREpsilon that, RenderingParams params) {
        final Point origin = params.getOrigin();
        Point loc = move(that.getEnter(), origin);
        r.drawLine(loc.x, loc.y, loc.x + that.getWidth(), loc.y);
    }

    public void renderSequence(RRSequence that, RenderingParams params) {
        final Point origin = params.getOrigin(); 
        List<ElementNode> children = params.isReverseSequence() 
                ? Lists.reverse(that.getChildren())
                : that.getChildren();
        Point contOrigin = move(origin, new Point(RRSequence.INDENT, 0));
        Point childOrigin = origin;
        int maxChunkHeight = 0;
        
        for (ElementNode child : children) {
            child.accept(this, params.copyOf().setOrigin(childOrigin));
            if (child.getHeight() > maxChunkHeight) { 
                maxChunkHeight = child.getHeight(); 
            }
            if (that.isTopLevel()) {
                if (child.getAttribute("linebreakAfter") != null) {
                    final Point contExit = move(childOrigin, child.getExit());
                    r.drawArrowRight(contExit.x+4, contExit.y);
                    r.drawLine(contExit.x, contExit.y, 4 + contExit.x, contExit.y);
                    contOrigin = move(contOrigin, new Point(0, maxChunkHeight));
                    final Point contEnter = move(contOrigin, child.getEnter()); // eigentlich schon nextChild
                    r.drawArrowRight(contEnter.x, contEnter.y);
                    childOrigin = contOrigin;
                    maxChunkHeight = 0;
                } else {
                    childOrigin = move(childOrigin, child.nextHorOrigin());
                }
            } else {
                childOrigin = move(childOrigin, child.nextHorOrigin());
            }
        }
    }

    public void renderPredicate(RRPredicate that, RenderingParams params) {
        final Point origin = params.getOrigin();
        final Point enter = move(that.getEnter(), origin);
        final Point exit = move(that.getExit(), origin);
        final Point childOrigin = move(origin, new Point(RRPredicate.EXT/2, 0));
        final String mark = that.isNegating() ? "!" : "&";
        
        if (params.isReverseSequence()) {
            predicateLeftBracket(enter, null);
            predicateRightBracket(exit, mark);
        } else {        // !params.isReverseSequences()
            predicateLeftBracket(enter, mark);
            predicateRightBracket(exit, null);
        }

        final ElementNode child = that.getChild();
        child.accept(this, params.copyOf().setOrigin(childOrigin));
    }
    
    public void renderZeroOrOne(RRZeroOrOne that, RenderingParams params) {
        that.getChild().accept(this, params);
    }

    public void renderZeroOrMore(RRZeroOrMore that, RenderingParams params) {
        final Point origin = params.getOrigin();
        final Point entry = move(that.getEnter(), origin);     // entry point of Choice structure
        final Point exit = move(that.getExit(), origin);       // exit point of Choice structure

        // draw shortcut connector
        r.drawLine(entry.x, entry.y, exit.x, exit.y);

        // draw child
        final Point childOrigin = move(origin, new Point(that.horMargin(), TOP));
        final ElementNode child = that.getChild();
        
        // render child
        final RenderingParams childParams = params.copyOf()
                .setOrigin(childOrigin)
                .negReverseSequence();
        child.accept(this, childParams);

        final Point chEntry = move(child.getEnter(), origin);
        final Point chExit = move(child.getExit(), origin);
        final int leftX = entry.x + that.hPad + RAD;
        final int rightX = exit.x - that.hPad - RAD;

        // draw connecting arcs
        r.quaterArc(leftX + RAD, entry.y + RAD, RAD, 2);
        r.quaterArc(leftX + RAD, chEntry.y + RAD, RAD, 3);
        r.quaterArc(rightX - RAD, exit.y + RAD, RAD, 1);
        r.quaterArc(rightX - RAD, chExit.y + RAD, RAD, 4);
        
        // draw direction marker
        if (childParams.isReverseSequence()) {
            r.drawArrowDown(rightX, exit.y + RAD, 5);
        } else {
            r.drawArrowDown(leftX, entry.y + RAD, 5);
        }
    }

    public void renderOneOrMore(RROneOrMore that, RenderingParams params) {
        final Point origin = params.getOrigin();
        int hPad = that.hPad;
        final Point entry = move(that.getEnter(), origin);       // self entry point
        final Point exit = move(that.getExit(), origin);         // self exit point
        final int leftX = entry.x + hPad;      // x-coordinate of left vertical connector line
        final int rightX = exit.x - hPad;      // x-coordinate of right vertical connector line

        // draw child
        final Point chOrigin = move(origin, new Point(that.horMargin(), 0));
        final ElementNode ch = that.getChild();

        ch.accept(this, params.copyOf().setOrigin(chOrigin));

        final int lowerY = chOrigin.y + ch.getHeight() + that.bMargin - RAD;

        // draw left,right and bottom horizontal connector lines
        r.drawLine(entry.x, entry.y, entry.x + that.horMargin(), entry.y);
        r.drawLine(exit.x, exit.y, exit.x - that.horMargin(), exit.y);
        r.drawLine(leftX + RAD, lowerY + RAD, rightX - RAD, lowerY + RAD);

        // draw left and right vertical connector lines
        r.drawLine(leftX, entry.y + RAD, leftX, lowerY);
        r.drawLine(rightX, exit.y + RAD, rightX, lowerY);

        // draw connecting arcs
        r.quaterArc(leftX + RAD, entry.y + RAD, RAD, 2);
        r.quaterArc(leftX + RAD, lowerY, RAD, 3);
        r.quaterArc(rightX - RAD, exit.y + RAD, RAD, 1);
        r.quaterArc(rightX - RAD, lowerY, RAD, 4);
    }

    public void renderLiteral(LiteralNode that, RenderingParams params) {
        final Point origin = params.getOrigin();
        Point loc;
        loc = move(that.getTextOrigin(), origin);
        drawText(that.getLabel(), loc);
        loc = move(that.getEnter(), origin);
        r.drawLine(loc.x, loc.y, loc.x + that.hPad, loc.y);
        loc = move(that.getExit(), origin);
        r.drawLine(loc.x, loc.y, loc.x - that.hPad, loc.y);
    }

    public void renderNonTerminal(RRNonTerminal that, RenderingParams params) {
        final Point origin = params.getOrigin();
        // draw text content and connector lines
        renderLiteral(that, params);
        // draw enclosing rectangle
        final Point loc = move(move(that.getEnter(), that.getRectOrigin()), origin);
        r.drawRect(loc.x, loc.y, that.getRectWidth(), that.getRectHeight());
    }

    public void renderTerminal(RRTerminal that, RenderingParams params) {
        final Point origin = params.getOrigin();
        // draw text content and connector lines
        renderLiteral(that, params);
        // draw enclosing rectangle
        final Point loc = move(move(that.getEnter(), that.getRectOrigin()), origin);
        r.drawRoundRect(loc.x, loc.y, that.getRectWidth(), that.getRectHeight(), RAD / 2);
    }

    private void drawText(RRLabel that, Point loc) {
        final URI uri = that.getUri();
        final String text = that.getLabel();
        if (uri == null) {
            r.drawText(loc.x, loc.y, text);
        } else {
            r.drawHyperlink(loc.x, loc.y, text, uri);
        }
    }
    
    private static final int PRED_BRACKET_SIZE = 3*RRPredicate.EXT/8;
    
    private void predicateLeftBracket(Point loc, String mark) {
        final int upperCornerY = loc.y - PRED_BRACKET_SIZE;
        final int lowerCornerY = loc.y + PRED_BRACKET_SIZE;
        r.drawLine(loc.x, upperCornerY, loc.x, lowerCornerY);
        r.drawLine(loc.x, upperCornerY, loc.x + PRED_BRACKET_SIZE, upperCornerY);
        r.drawLine(loc.x, lowerCornerY, loc.x + PRED_BRACKET_SIZE, lowerCornerY);
        drawMarkLeft(loc, mark);
        if (mark == null) {
            r.drawTriangleLeft(loc.x + RRPredicate.EXT/2 - HPAD/2, loc.y, HPAD/2);
        }
    }

    private void predicateRightBracket(Point loc, String mark) {
        final int yUpperCorner = loc.y - PRED_BRACKET_SIZE;
        final int yLowerCorner = loc.y + PRED_BRACKET_SIZE;
        r.drawLine(loc.x, yUpperCorner, loc.x, yLowerCorner);
        r.drawLine(loc.x, yUpperCorner, loc.x - PRED_BRACKET_SIZE, yUpperCorner);
        r.drawLine(loc.x, yLowerCorner, loc.x - PRED_BRACKET_SIZE, yLowerCorner);
        drawMarkRight(loc, mark);
        if (mark == null) {
            r.drawTriangleRight(loc.x - RRPredicate.EXT/2 + HPAD/2, loc.y, HPAD/2);
        }
    }
    
    private void drawMarkRight(Point loc, String mark) {
        if (mark != null) {
            r.drawText(loc.x - PRED_BRACKET_SIZE - 2, loc.y + 5, mark, "blue");
        }
    }
    
    private void drawMarkLeft(Point loc, String mark) {
        if (mark != null) {
            r.drawText(loc.x + PRED_BRACKET_SIZE - 5, loc.y + 5, mark, "blue");
        }        
    }

    // Geometric transformations
    
    private static Point move(Point loc, Point by) {
        return new Point(loc.x + by.x, loc.y + by.y);
    }

    private static Point mirrorH(Point loc, Point by) {
        return new Point(2*by.x - loc.x, loc.y);
    }

    private static Point mirrorV(Point loc, Point by) {
        return new Point(loc.x, 2*by.y - loc.y);
    }

    private static Point flip(Point loc, Point by) {
        return mirrorH(mirrorV(loc, by), by);
    }

}
