package kmworks.dsltools.ast.base;


import xtc.tree.Node;

/**
 * Created by cpl on 18.03.2017.
 */
public abstract class ASTNode extends Node {

    /*
    private final Annotations annotations = new Annotations();
    public Annotations annotations() {
        return annotations;
    }
    */

    private Span span;
    public Span span() {
        return span;
    }
    public void setSpan(Span span) {
        this.span = span;
    }

    public abstract String toString();

}
