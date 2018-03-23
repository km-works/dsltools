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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import java.util.stream.Collectors;
import kmworks.dsltools.parser.base.AbstractParser;
import kmworks.dsltools.parser.base.ParserFactory;
import kmworks.dsltools.util.PropertiesManager;
import kmworks.dsltools.util.xml.XSLTransformerFactory;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.canonical.Canonicalizer;
import xtc.parser.Result;

/**
 *
 * @author Christian P. Lerch
 * @version 1.0.0
 * @since 1.0
 */
public class RRD_Translator {

    private final AbstractParser parser;
    private final String source;            // source input string
    
    private List<String> diagrams;
    List<String> names;
    private String error;
    private boolean isError;

    // measure execution timing
    private long startNanoTime;
    private long endNanoTime;

    public RRD_Translator(String language, String source) {
        this.source = source.trim();
        this.parser = ParserFactory.newParser(language, new StringReader(this.source));
    }

    public void translate() {
        startNanoTime = System.nanoTime();

        try {
            Result parseResult = parser.parse();
            if (parseResult != null) {
                if (parseResult.hasValue()) {                    
                    Document xmlAST = new Document((Element)parseResult.semanticValue());
                    xmlAST = XSLTransformerFactory.doTransform(xmlAST, "ML-canonical");

                    ElementNode rrdAST = new XMLtoRRD_Transformer().transform(xmlAST.getRootElement());

                    SVGRenderer renderer = new SVGRenderer(PropertiesManager.getOptions());
                    SVGRenderingVisitor visitor = new SVGRenderingVisitor(renderer);
                    rrdAST.accept(visitor, new RenderingParams());

                    names = renderer.getNames();
                    
                    diagrams = renderer.getParts().stream()
                            .map(e -> serialize(e))
                            .collect(Collectors.toList());
                    
                    isError = false;
                } else {
                    error = parser.fmtErrorMsg(source, parseResult.parseError());
                    isError = true;
                }
            } else {
                error = "Parser returned no result";
                isError = true;
            }
        } catch (Exception ex) {
            error = "Translator failed: " + ex.getMessage();
            isError = true;
        }
        
        endNanoTime = System.nanoTime();
    }
    
    public List<String> getNames() {
        return names;
    }
    
    public List<String> getResult() {
        return diagrams;
    }

    public String getError() {
        return error;
    }

    public boolean hasError() {
        return isError;
    }

    public long getNanoTiming() {
        return endNanoTime - startNanoTime;
    }

    protected static String serialize(Element elem) {
        String result = null;
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            Canonicalizer c = new Canonicalizer(out);
            c.write(elem);
            result = out.toString("UTF-8");
        } catch (IOException ex) {}
        return result;
    }
}
