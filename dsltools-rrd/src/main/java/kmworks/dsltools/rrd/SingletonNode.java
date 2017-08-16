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
 * Holder for a single child element.
 *
 * @author Christian P. Lerch
 * @version 1.0.0
 * @since 1.0
 */
public abstract class SingletonNode extends ElementNode {

    private ElementNode child = null;

    protected SingletonNode() {
        super();
    }

    @Override
    public void addChild(ElementNode child) {
        if (this.child == null) {
            this.child = child;
        } else {
            throw new UnsupportedOperationException("Cannot add more than one child to a singleton element");
        }
    }

    public int getSize() {
        return 1;
    }
    
    public final ElementNode getChild() {
        return child;
    }

}
