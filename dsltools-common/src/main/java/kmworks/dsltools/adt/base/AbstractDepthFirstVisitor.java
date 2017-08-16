package kmworks.dsltools.adt.base;

import kmworks.util.ds.PeekingArrayListStack;
import kmworks.util.ds.PeekingStack;

/**
 * Created by cpl on 21.03.2017.
 */
public abstract class AbstractDepthFirstVisitor<E> {

    private final PeekingStack<E> curr = new PeekingArrayListStack<>(256);

    public abstract E newElement(ADTNode node);
    public abstract E newElement(String name);
    public abstract void addElement(E elem);

    public E top() {
        return curr.top();
    }

    public void push(E elem) {
        curr.push(elem);
    }

    public E pop() {
        return curr.pop();
    }

    public void pushFor(ADTNode node) {
        E elem = newElement(node);
        addElement(elem);
        push(elem);
    }
    
    public void pushFor(String name) {
        E elem = newElement(name);
        addElement(elem);
        push(elem);
    }

}
