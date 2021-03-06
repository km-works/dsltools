/*
 *   Copyright (C) 2012 Christian P. Lerch, Vienna, Austria.
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
 *   with this distribution. If not, see <http://km-works.eu/res/GPL.txt> or
 *   <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package kmworks.dsltools.util.xml;

import kmworks.util.strings.StringEscapeUtil;
import nu.xom.Attribute;
import nu.xom.Element;
import nu.xom.Node;
import nu.xom.Text;

/**
 * @author Christian P. Lerch
 * @version 1.0.0
 * @since 1.0
 * <p>
 * Convert an XML xom tree to an equivalent ATDL (Abstract Tree Description Language) string
 */
public final class XMLtoATDL_Serializer {

    private XMLtoATDL_Serializer() {
    }

    public static String write(Element node) {

        StringBuilder sb = new StringBuilder();
        sb.append('(').append(node.getQualifiedName());

        for (int i = 0; i < node.getAttributeCount(); i++) {
            Attribute attr = node.getAttribute(i);
            sb.append(" @").append(attr.getQualifiedName()).append('=').append('"');
            String attrValue = StringEscapeUtil.unescapeXml(attr.getValue());
            sb.append(escapeATDL(attrValue));
            sb.append('"');
        }
        for (int i = 0; i < node.getChildCount(); i++) {
            Node child = node.getChild(i);
            if (child instanceof Element) {
                sb.append(' ').append(write((Element) child));
            } else if (child instanceof Text) {
                sb.append(' ').append('"');
                String textValue = ((Text) child).getValue();
                sb.append(escapeATDL(textValue));
                sb.append('"');
            }
        }
        sb.append(')');
        String result = sb.toString();
        return result;
    }

    private static String escapeATDL(String s) {

        StringBuilder sb = new StringBuilder();

        for (char ch : s.toCharArray()) {
            switch (ch) {
                case '"':
                    sb.append("\\\"");
                    break;
                case '\\':
                    sb.append("\\\\");
                    break;
                case '\f':
                    sb.append("\\f");
                    break;
                case '\n':
                    sb.append("\\n");
                    break;
                case '\r':
                    sb.append("\\r");
                    break;
                case '\t':
                    sb.append("\\t");
                    break;
                default:
                    sb.append(ch);
            }
        }

        return sb.toString();
    }

}
