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
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Christian P. Lerch
 */
public abstract class ElementNode {

    // Geometry constants
    public static final int RAD = 16;
    public static final int HPAD = RAD / 2;
    public static final int TOP = 2 * RAD;  // do NOT change !

    // Default geometry parameters
    protected int hPad = HPAD;       // horizontal padding
    protected int tPad = 0;          // vertical top padding
    protected int bPad = 0;          // vertical bottom padding
    protected int tMargin = 0;       // vertical top margin
    protected int bMargin = 0;       // vertical bottom margin

    private final Map<String, Object> attributes = new HashMap<>();
    
    protected ElementNode() {
    }
    
    public Object getAttribute(String name) {
        return attributes.get(name);
    }
    
    public void setAttribute(String name, Object value) {
        attributes.put(name, value);
    }

    public abstract void addChild(ElementNode child);

    // Accept rendering visitor
    public abstract void accept(SVGRenderingVisitor v, RenderingParams params);

    // Compute geometry properties
    public abstract int getWidth();
    public abstract int getHeight();

    public Point nextHorOrigin() {
        return new Point(getWidth(), 0);
    }

    public Point nextVerOrigin() {
        return new Point(0, getHeight());
    }

    public Point getEnter() {
        return new Point(0, TOP);
    }

    public Point getExit() {
        return new Point(getWidth(), TOP);
    }

    public int horMargin() {
        return hPad + 2 * RAD;
    }

    public Point moveHorMargin() {
        return new Point(horMargin(), 0);
    }

}
