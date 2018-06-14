package kmworks.dsltools.ast.adt;

/**
 * Created by cpl on 21.03.2017.
 */

import com.google.common.base.Optional;
import kmworks.dsltools.ast.base.*;
import kmworks.dsltools.ast.base.Seq;
import org.immutables.value.Value;

@Value.Immutable
public abstract class TypeDef extends ASTNode {
    @Value.Parameter public abstract String name();
    @Value.Parameter public abstract Optional<String> kind();
    @Value.Parameter public abstract Seq<Parameter> parameters();
    @Value.Parameter public abstract Seq<TypeDef> subTypes();
}
