package kmworks.dsltools.adt.ml;

import kmworks.dsltools.adt.base.AbstractRoot;
import org.immutables.value.Value;

/**
 * Created by cpl on 18.03.2017.
 */
@Value.Immutable
public abstract class Production extends AbstractRoot {
    @Value.Parameter public abstract String name();
    @Value.Parameter public abstract Choice choice();
}
