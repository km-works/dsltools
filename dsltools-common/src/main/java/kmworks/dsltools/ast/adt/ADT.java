package kmworks.dsltools.ast.adt;

import kmworks.dsltools.ast.base.ASTNode;
import kmworks.dsltools.ast.base.Seq;
import org.immutables.value.Value;

/**
 * Created by cpl on 21.03.2017.
 */
@Value.Immutable
public abstract class ADT extends ASTNode {

    @Value.Parameter
    public abstract String name();

    @Value.Parameter
    public abstract Seq<TypeDef> main();

    @Value.Parameter
    public abstract Seq<TypeDef> aux();

}
