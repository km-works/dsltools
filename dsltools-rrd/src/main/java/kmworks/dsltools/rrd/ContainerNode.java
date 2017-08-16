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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Christian P. Lerch
 * @version 1.0.0
 * @since 1.0
 */
public abstract class ContainerNode extends ElementNode {
    
    private final List<ElementNode> children = new ArrayList<>();

    protected ContainerNode() {
        super();
        hPad = 0;
    }

    public ContainerNode(ElementNode... children) {
        this();
        this.children.addAll(Arrays.asList(children));
    }

    public final List<ElementNode> getChildren() {
        return children;
    }
    
    @Override
    public void addChild(ElementNode child) {
        children.add(child);
    }
    
    public final ElementNode getFirstChild() {
        return getChild(0);
    }
    
    public final ElementNode getChild(int i) {
        return children.get(i);
    }
    
    public final int getSize() {
        return children.size();
    }
    
    public int sumWidth() {
        int sum = 0;
        for (ElementNode ch : getChildren()) {
            sum += ch.getWidth();
        }
        return sum;
    }

    public int maxWidth() {
        int max = getChildren().get(0).getWidth();
        for (int i = 1; i < getChildren().size(); i++) {
            int tst = getChildren().get(i).getWidth();
            if (tst > max) {
                max = tst;
            }
        }
        return max;
    }

    public int sumHeight() {
        int sum = 0;
        for (ElementNode ch : getChildren()) {
            sum += ch.getHeight();
        }
        return sum;
    }

    public int maxHeight() {
        int max = getChildren().get(0).getHeight();
        for (int i = 1; i < getChildren().size(); i++) {
            int tst = getChildren().get(i).getHeight();
            if (tst > max) {
                max = tst;
            }
        }
        return max;
    }

}
