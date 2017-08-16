package kmworks.dsltools.adt.ml;

import kmworks.dsltools.adt.base.*;
import org.immutables.value.Value;

/**
 * Created by cpl on 18.03.2017.
 */
@Value.Immutable
public abstract class Predicate extends Term {
    @Value.Parameter public abstract PredicateType type();
    @Value.Parameter public abstract Predicable arg();
}
