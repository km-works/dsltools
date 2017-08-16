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

import java.awt.Point;
import java.net.URI;

/**
 *
 * @author Christian P. Lerch
 * @version 1.0.0
 * @since 1.0
 */
public abstract class LiteralNode extends LeaveNode {

    private static final int TEXT_MARGIN = 8;

    private final RRLabel label;

    protected LiteralNode(String text) {
        this(text, null);
    }

    protected LiteralNode(String text, URI uri) {
        super();
        label = new RRLabel(text, uri);
        tPad = 5;
        bPad = 5;
        tMargin = 5;
        bMargin = 5;
    }

    @Override
    public final int getWidth() {
        return 2 * (hPad + TEXT_MARGIN) + label.getTextWidth();
    }

    @Override
    public final int getHeight() {
        return TOP + label.getTextHeight() / 2 + bMargin + bPad;
    }

    public RRLabel getLabel() {
        return label;
    }

    public Point getRectOrigin() {
        return new Point(hPad, -(label.getTextHeight() / 2 + tMargin));
    }

    public int getRectHeight() {
        return label.getTextHeight() + tMargin + bMargin;
    }

    public int getRectWidth() {
        return label.getTextWidth() + 2 * TEXT_MARGIN;
    }

    public Point getTextOrigin() {
        return new Point(hPad + TEXT_MARGIN, TOP + label.getTextHeight() / 2 - 2);
    }

}
