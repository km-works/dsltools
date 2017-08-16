package kmworks.dsltools.adt.adt;

import kmworks.dsltools.adt.base.AbstractRoot;
import org.immutables.value.Value;

/**
 * Created by cpl on 21.03.2017.
 */
@Value.Immutable
public abstract class Parameter extends AbstractRoot {
    @Value.Parameter public abstract String name();
    @Value.Parameter public abstract TypeRef typeRef();
}
