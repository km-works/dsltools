package kmworks.dsltools.ast.adt;

import java.util.List;

/**
 * Created by cpl on 21.03.2017.
 */
public final class NodeWalker {

    private final NodeVisitor visit;

    public NodeWalker(NodeVisitor visitor) {
        this.visit = visitor;
    }
    
    public void walkGrammar(Grammar node) {
        visit.forGrammar(node);
        walkADT(node.adt());
        visit.endGrammar(node);
    }

    public void walkADT(ADT node) {
        visit.forADT(node);
        node.main().list().stream().forEach(n -> walkTypeDef(n));
        walkAux(node.aux().list());
        visit.endADT(node);
    }

    void walkTypeDef(TypeDef node) {
        visit.forTypeDef(node);
        node.parameters().list().stream().forEach(n -> walkParameter(n));
        node.subTypes().list().stream().forEach(n -> walkTypeDef(n));
        visit.endTypeDef(node);
    }

    void walkParameter(Parameter node) {
        visit.forParameter(node);
        visit.endParameter(node);
    }
    
    void walkAux(List<TypeDef> list) {
        visit.forAux();
        list.stream().forEach(n -> walkTypeDef(n));
        visit.endAux();
    }
    
}
