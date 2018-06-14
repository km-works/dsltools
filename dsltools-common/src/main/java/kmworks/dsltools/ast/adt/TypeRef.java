package kmworks.dsltools.ast.adt;

import com.google.common.base.Optional;
import kmworks.dsltools.ast.base.Multiplicity;
import kmworks.dsltools.ast.base.ASTNode;
import org.immutables.value.Value;

/**
 * Created by cpl on 22.03.2017.
 */
@Value.Immutable
public abstract class TypeRef extends ASTNode {
    @Value.Parameter public abstract String name();
    @Value.Parameter public abstract Optional<Multiplicity> multi();
}
