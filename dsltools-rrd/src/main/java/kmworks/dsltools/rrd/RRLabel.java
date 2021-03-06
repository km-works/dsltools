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

import java.net.URI;

/**
 *
 * @author Christian P. Lerch
 * @version 1.0.0
 * @since 1.0
 */
public final class RRLabel {

  private final URI uri;
  private final String label;
  private final int textWidth;
  private final int textHeight;

  protected RRLabel(String label) {
    this(label, null);
  }

  protected RRLabel(String label, URI uri) {
    this.label = label;
    this.uri = uri;
    TextMetric textProp = SVGRenderer.measureText(label);
    this.textWidth = (int) Math.round(textProp.getWidth()) ;
    this.textHeight = (int) Math.round(textProp.getHeight());
  }

  public String getLabel() {
    return label;
  }

  public int getTextHeight() {
    return textHeight;
  }

  public int getTextWidth() {
    return textWidth;
  }

  public URI getUri() {
    return uri;
  }

}
