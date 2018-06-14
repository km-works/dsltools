package kmworks.dsltools.ast.adt;

import kmworks.dsltools.ast.base.ASTNode;
import org.immutables.value.Value;

/**
 * Created by cpl on 21.03.2017.
 */
@Value.Immutable
public abstract class Parameter extends ASTNode {
    @Value.Parameter public abstract String name();
    @Value.Parameter public abstract TypeRef typeRef();
}
