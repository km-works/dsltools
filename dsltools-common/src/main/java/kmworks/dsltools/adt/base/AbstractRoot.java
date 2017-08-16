package kmworks.dsltools.adt.base;


/**
 * Created by cpl on 18.03.2017.
 */
public abstract class AbstractRoot implements ADTNode {
    
    private final Annotations annotations = new Annotations();
    
    public Annotations annotations() {
        return annotations;
    }
    
}
