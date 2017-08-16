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
package kmworks.dsltools.util.xml;

import com.google.common.io.Resources;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Node;
import nu.xom.Nodes;
import nu.xom.ParsingException;
import nu.xom.xslt.XSLException;
import nu.xom.xslt.XSLTransform;

/**
 *
 * @author Christian P. Lerch
 */
public final class XSLTransformerFactory {
    
    private static final XSLTransformerFactory INSTANCE = new XSLTransformerFactory();
    
    private XSLTransformerFactory() {}
    
    private final Map<String, XSLTransform> cache = new HashMap<>();
    
    public static XSLTransform getTransform(String transformName) {
        XSLTransform result = INSTANCE.cache.get(transformName);
        if (result == null) {
            String xslResource = transformName + ".xsl";
            try (final InputStream stream = Resources.getResource(XSLTransformerFactory.class, xslResource).openStream()) {
                Builder builder = new Builder();
                try {
                    Document stylesheet = builder.build(stream);
                    result = new XSLTransform(stylesheet);
                } catch (ParsingException | IOException | XSLException ex) {
                    throw new RuntimeException("Error building XSLTransformer: " + ex.getMessage());
                }
            } catch (IOException ex) {
                throw new RuntimeException("XSLTransform-source not found: " + xslResource);
            }
            INSTANCE.cache.put(transformName, result);
        }
        return result;
    }
    
    public static Document doTransform(Document xmlAST, String transformerName) {
        XSLTransform transformer = getTransform(transformerName);
        Nodes xmlResult;
        try {
            xmlResult = transformer.transform(xmlAST);
        } catch (XSLException ex) {
            throw new RuntimeException("Transform failed: " + ex.getMessage());
        }
        for (Node node : xmlResult.list()) {
            if (node instanceof Element) {
                Element elem = (Element)node;
                if (elem.getLocalName().equals("Grammar")) {
                    return new Document(elem);
                }
            }
        }
        throw new RuntimeException("Grammar element not found");
    }
    
}
