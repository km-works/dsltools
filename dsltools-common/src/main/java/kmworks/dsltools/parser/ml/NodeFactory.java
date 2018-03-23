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
package kmworks.dsltools.parser.ml;

import java.util.List;
import kmworks.dsltools.adt.base.Multiplicity;
import kmworks.dsltools.adt.base.PredicateType;
import kmworks.dsltools.util.xml.Namespaces;
import kmworks.util.StringEscapeUtil;
import nu.xom.Attribute;
import nu.xom.Element;
import xtc.util.Pair;

/**
 * Created by cpl on 18.03.2017.
 */
public class NodeFactory {
    
    public static Element mkGrammar(Pair<Element> productions) {
        Element result = mkElem("Grammar");
        result.addNamespaceDeclaration(Namespaces.AN_PREFIX, Namespaces.AN_URI);
        appendAll(result, productions);
        return result;
    }
    
    public static Element mkProduction(String name, Element choice) {
        Element result = mkElem("Production");
        result.addAttribute(mkAttr("name", name));
        result.appendChild(choice);
        return result;
    }

    public static Element mkChoice(Element firstSeq, Pair<Element> moreSeq, boolean epsilon) {
        Element result = mkElem("Choice");
        if (epsilon) {
            result.addAttribute(mkAttr("epsilon", Boolean.toString(epsilon)));
        }
        result.appendChild(firstSeq);
        appendAll(result, moreSeq);
        return result;
    }
    
    public static Element mkSequence(Element hd, Pair<Element> tl) {
        Element result = mkElem("Sequence");
        result.appendChild(hd);
        appendAll(result, tl);
        return result;
    }
    
    public static Element linebreakTerm(Element term, boolean lineBreak) {
        if (lineBreak) { // add attribute @an:linebreakAfter=true
            term.addAttribute(mkAnnotationAttr("linebreakAfter", "true"));
        }
        return term;
    }
    
    public static Element mkTerm(Character prefix, Element primary, Character suffix) {
        if (prefix != null) {
            if (suffix != null) { // prefix != null && suffix != null
                return mkPredicate(prefix, mkMultiple(primary, suffix));
            } else { // prefix != null && suffix == null
                return mkPredicate(prefix, primary);
            }
        } else { // prefix == null 
            if (suffix != null) { // prefix == null && suffix != null
                return mkMultiple(primary, suffix);
            } else { // prefix == null && suffix == null
                return primary;
            }
        }
    }
    
    private static Element mkPredicate(char prefix, Element primary) {
        Element result = mkElem("Predicate");
        String type;
        switch (prefix) {
            case '&':
                type = PredicateType.MustMatch.name();
                break;
            case '!':
                type = PredicateType.MustNotMatch.name();
                break;
            default:
                throw new IllegalArgumentException("Invalid predicate type: " + prefix);
        }
        result.addAttribute(mkAttr("type", type));
        result.appendChild(primary);
        return result;    
    }
    
    private static Element mkMultiple(Element primary, char suffix) {
        Element result = mkElem("Multiple");
        String multi;
        switch (suffix) {
            case '?':
                multi = Multiplicity.ZeroOrOne.name();
                break;
            case '*':
                multi = Multiplicity.ZeroOrMore.name();
                break;
            case '+':
                multi = Multiplicity.OneOrMore.name();
                break;
            default:
                throw new IllegalArgumentException("Invalid primary multiplicity: " + suffix);
        }
        result.addAttribute(mkAttr("multiplicity", multi));
        result.appendChild(primary);
        return result;
    }

    public static Element mkNonTerminal(String name) {
        Element result = mkElem("NonTerminal");
        result.addAttribute(mkAttr("name", name));
        return result;
    }
    
    public static Element mkLiteral(String caption) {
        Element result = mkElem("Literal");
        result.addAttribute(mkAttr("caption", caption));
        return result;
    }

    public static Element mkAnyChar() {
        return mkElem("AnyChar");
    }

    public static Element mkCharClass(Pair<List<String>> rangeList) {
        Element result = mkElem("CharClass");
        StringBuilder sb = new StringBuilder("[");
        for (List<String> range : rangeList) {
            final char ch1 = StringEscapeUtil.unescapeJava(range.get(0)).charAt(0);
            sb.append(ch1);
            if (range.size() == 1) {
                result.appendChild(mkCharRange(ch1, ch1));
            } else /* (range.size() == 2) */ {
                final char ch2 = StringEscapeUtil.unescapeJava(range.get(1)).charAt(0);
                result.appendChild(mkCharRange(ch1, ch2));
                sb.append('-').append(ch2);
            }
        }
        String value = rangeList.list().toString();
        result.addAttribute(mkAttr("value", value));
        result.addAttribute(mkAttr("caption", sb.append(']').toString()));
        return result;
    }
    
    private static Element mkCharRange(char ch1, char ch2) {
        Element result = mkElem("CharRange");
        if (ch1 == ch2) {
            result.addAttribute(mkAttr("first", ch1));
        } else {
            result.addAttribute(mkAttr("first", ch1));
            result.addAttribute(mkAttr("last", ch2));
        }
        return result;
    }


    private static Element mkElem(String name) {
        return new Element(name);
    }
    
    private static Attribute mkAttr(String name, String value) {
        return new Attribute(name, value);
    }
    
    private static Attribute mkAttr(String name, char value) {
        return new Attribute(name, Character.toString(value));
    }
    
    private static Attribute mkAnnotationAttr(String name, String value) {
        return new Attribute(Namespaces.AN_PREFIX + ":" + name, Namespaces.AN_URI, value, Attribute.Type.CDATA);
    }
    
    private static void appendAll(Element result, Pair<Element> children) {
        for (Element child : children) {
            result.appendChild(child);
        }
    }

}