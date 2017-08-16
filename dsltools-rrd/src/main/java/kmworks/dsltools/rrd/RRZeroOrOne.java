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

/** 
 * Represents an optional grammar element.
 * Implemented as an epsilon-choice with a single child element.
 *
 * @author Christian P. Lerch
 * @version 1.0.0
 * @since 1.0
 */
public final class RRZeroOrOne extends SingletonNode {

    protected RRZeroOrOne() {
        super();
        RRChoice epsilonChoice = new RRChoice();
        epsilonChoice.addChild(new RREpsilon());
        super.addChild(epsilonChoice);
    }

    @Override
    public void accept(SVGRenderingVisitor v, RenderingParams params) {
        v.renderZeroOrOne(this, params);
    }

    @Override
    public void addChild(ElementNode child) {
        RRChoice choice = (RRChoice)getChild();
        if (choice.getSize() == 1) {
            choice.addChild(child);
        } else {
            throw new UnsupportedOperationException("Cannot add more than one child to ZeroOrMore element");
        }
    }

    @Override
    public int getWidth() {
        return getChild().getWidth();
    }

    @Override
    public int getHeight() {
        return getChild().getHeight();
    }

}
