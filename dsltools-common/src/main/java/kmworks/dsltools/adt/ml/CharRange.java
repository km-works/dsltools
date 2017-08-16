package kmworks.dsltools.adt.ml;

import com.google.common.base.Optional;
import kmworks.dsltools.adt.base.*;
import org.immutables.value.Value;

/**
 * Created by cpl on 18.03.2017.
 */
@Value.Immutable
public abstract class CharRange extends AbstractRoot {
    @Value.Parameter public abstract char first();
    @Value.Parameter public abstract Optional<Character> last();
}
