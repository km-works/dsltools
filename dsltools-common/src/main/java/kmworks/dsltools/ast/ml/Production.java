package kmworks.dsltools.ast.ml;

import kmworks.dsltools.ast.base.ASTNode;
import org.immutables.value.Value;

/**
 * Created by cpl on 18.03.2017.
 */
@Value.Immutable
public abstract class Production extends ASTNode {
    @Value.Parameter public abstract String name();
    @Value.Parameter public abstract Choice choice();
}
