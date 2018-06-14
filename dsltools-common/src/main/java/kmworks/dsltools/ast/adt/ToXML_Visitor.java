package kmworks.dsltools.ast.adt;

import kmworks.dsltools.ast.base.ASTNode;
import nu.xom.Attribute;
import nu.xom.Document;
import nu.xom.Element;

/**
 * Created by cpl on 21.03.2017.
 */
public final class ToXML_Visitor extends AbstractNodeVisitor<Element> {

    private final Document doc;
    private final NodeWalker walker;

    public ToXML_Visitor() {
        super();
        walker = new NodeWalker(this);
        doc = new Document(newElement("ROOT"));
        push(doc.getRootElement());
    }

    public Document visit(Grammar node) {
        walker.walkGrammar(node);
        return doc;
    }
    
    @Override
    public Element newElement(String name) {
        return new Element(name);
    }

    @Override
    public Element newElement(ASTNode node) {
        String name = node.getClass().getSimpleName();
        if (name.endsWith("Impl")) {
            name = name.substring(0, name.length() - 4);
        }
        return newElement(name);
    }

    @Override
    public void addElement(Element elem) {
        top().appendChild(elem);
    }

    /*
        Transform Grammar elements to XML
     */
    @Override
    public void forGrammar(Grammar node) {
        super.forGrammar(node);
        top().addAttribute(mkAttribute("pkgName", node.pkgName().list()));
    }
    
    @Override
    public void forADT(ADT node) {
        super.forADT(node);
        top().addAttribute(mkAttribute("name", node.name()));
    }
    
    @Override
    public void forTypeDef(TypeDef node) {
        super.forTypeDef(node);
        top().addAttribute(mkAttribute("name", node.name()));
        if (node.kind().isPresent()) {
            top().addAttribute(mkAttribute("kind", node.kind().get()));
        }
    }

    @Override
    public void forParameter(Parameter node) {
        super.forParameter(node);
        top().addAttribute(mkAttribute("name", node.name()));
        TypeRef typeRef = node.typeRef();
        top().addAttribute(mkAttribute("type", typeRef.name()));
        if (typeRef.multi().isPresent()) {
            top().addAttribute(mkAttribute("multi", typeRef.multi().get()));
        }
    }

    private Attribute mkAttribute(String name, Object obj) {
        return new Attribute(name, obj.toString());
    }

}
