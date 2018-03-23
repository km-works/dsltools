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

import java.util.List;
import kmworks.util.config.PropertyMap;
import nu.xom.Element;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author Christian P. Lerch
 */
public class SVGRendererTest {
    
    public SVGRendererTest() {
    }

    @Test
    public void testSvgRootElement() {
        PropertyMap opts = PropertyMap.of();
        SVGRenderer renderer = new SVGRenderer(opts);
        renderer.beginSVGElement(100, 100);
        List<Element> result = renderer.getParts();
        assertEquals(1, result.size());
        String serialized = RRD_Translator.serialize(result.get(0));
        assertEquals(serialized, "<svg xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\" baseProfile=\"full\" height=\"100px\" style=\"stroke:black; font-family:SansSerif; font-size:12px; \" version=\"1.1\" width=\"100px\"><g display=\"block\"></g></svg>");
        System.out.println(serialized);
    }
    
}
