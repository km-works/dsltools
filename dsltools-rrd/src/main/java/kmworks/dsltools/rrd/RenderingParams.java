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

/**
 *
 * @author Christian P. Lerch
 */
public class RenderingParams {
    
    private Point origin;
    private boolean reverseSequence = false;
    
    public RenderingParams() {
        this(new Point(0, 0));
    }
    
    public RenderingParams(Point origin) {
        this(origin, false);
    }
    
    public RenderingParams(Point origin, boolean reverseSequences) {
        this.origin = origin;
        this.reverseSequence = reverseSequences;
    }
    
    public RenderingParams(RenderingParams that) {
        this(that.origin, that.reverseSequence);
    }
    
    public Point getOrigin() {
        return origin;
    }
    
    public RenderingParams setOrigin(Point origin) {
        this.origin = origin;
        return this;
    }
    
    public boolean isReverseSequence() {
        return reverseSequence;
    }
    
    public RenderingParams negReverseSequence() {
        reverseSequence = !reverseSequence;
        return this;
    }
    
    public RenderingParams copyOf() {
        return new RenderingParams(this);
    }
    
}
