package kmworks.dsltools.adt.adt;

import kmworks.dsltools.adt.base.AbstractDepthFirstVisitor;

/**
 * Created by cpl on 21.03.2017.
 */
public abstract class AbstractNodeVisitor<E> extends AbstractDepthFirstVisitor<E> implements NodeVisitor {
    
    @Override
    public void forGrammar(Grammar node) {
        pushFor(node);
    }
    
    @Override
    public void endGrammar(Grammar node) {
        pop();
    }

    @Override
    public void forADT(ADT node) {
        pushFor(node);
    }
    
    @Override
    public void endADT(ADT node) {
        pop();
    }

    @Override
    public void forTypeDef(TypeDef node) {
        pushFor(node);
    }

    @Override
    public void endTypeDef(TypeDef node) {
        pop();
    }

    @Override
    public void forParameter(Parameter node) {
        pushFor(node);
    }

    @Override
    public void endParameter(Parameter node) {
        pop();
    }
    
    @Override
    public void forAux() {
        pushFor("AUX");
    }
    
    @Override
    public void endAux() {
        pop();
    }

}
