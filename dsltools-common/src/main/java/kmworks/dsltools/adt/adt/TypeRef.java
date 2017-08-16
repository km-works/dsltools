package kmworks.dsltools.adt.adt;

import com.google.common.base.Optional;
import kmworks.dsltools.adt.base.Multiplicity;
import kmworks.dsltools.adt.base.AbstractRoot;
import org.immutables.value.Value;

/**
 * Created by cpl on 22.03.2017.
 */
@Value.Immutable
public abstract class TypeRef extends AbstractRoot {
    @Value.Parameter public abstract String name();
    @Value.Parameter public abstract Optional<Multiplicity> multi();
}
