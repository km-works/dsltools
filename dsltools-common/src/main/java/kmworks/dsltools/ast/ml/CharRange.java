package kmworks.dsltools.ast.ml;

import com.google.common.base.Optional;
import kmworks.dsltools.ast.base.*;
import org.immutables.value.Value;

/**
 * Created by cpl on 18.03.2017.
 */
@Value.Immutable
public abstract class CharRange extends ASTNode {
    @Value.Parameter public abstract char first();
    @Value.Parameter public abstract Optional<Character> last();
}
