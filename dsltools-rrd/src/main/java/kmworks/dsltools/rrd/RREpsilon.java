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
public final class RREpsilon extends LeaveNode {

  public RREpsilon() {
    super();
  }

  @Override
  public void accept(SVGRenderingVisitor v, RenderingParams params) {
    v.renderEpsilon(this, params);
  }

  @Override
  public int getWidth() {
    return 1;
  }

  @Override
  public int getHeight() {
    return TOP;
  }

}
