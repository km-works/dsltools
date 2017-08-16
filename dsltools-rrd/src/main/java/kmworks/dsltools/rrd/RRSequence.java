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
 *
 * @author Christian P. Lerch
 * @version 1.0.0
 * @since 1.0
 */
public final class RRSequence extends ContainerNode {
    
    public static final int INDENT = 2*HPAD; 
    
    /** Flag a top-level sequence.
     * A RRSequence is top-level iff it is a RRProduction grandchild (RRProduction/RRChoice/RRSequence). 
     * Top-level sequences may be split into continuation lines.
     */
    private boolean topLevel = false;

    protected RRSequence() {
        super();
    }

    @Override
    public void accept(SVGRenderingVisitor v, RenderingParams params) {
        v.renderSequence(this, params);
    }

    @Override
    public int getWidth() {
        int chunkCount = 0;
        int maxChunkWidth = 0;
        int sumChildWidth = 0;
        for (ElementNode child : getChildren()) {
            sumChildWidth += child.getWidth();
            if (child.getAttribute("linebreakAfter") != null) {
                chunkCount++;
                if (sumChildWidth > maxChunkWidth) maxChunkWidth = sumChildWidth;
                sumChildWidth = 0;
            }
        }
        if (sumChildWidth > maxChunkWidth) maxChunkWidth = sumChildWidth;
        return maxChunkWidth + (chunkCount>0 ? INDENT : 0);
    }

    @Override
    public int getHeight() {
        int totalHeight = 0;
        int maxChildHeight = 0;
        for (ElementNode child : getChildren()) {
            if (child.getHeight() > maxChildHeight) maxChildHeight = child.getHeight();
            if (child.getAttribute("linebreakAfter") != null) {
                totalHeight += maxChildHeight;
                maxChildHeight = 0;
            }
        }
        return totalHeight + maxChildHeight;
    }
    
    public boolean isTopLevel() {
        return topLevel;
    }
    
    public void setTopLevel() {
        topLevel = true;
    }
    
}
