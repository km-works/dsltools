package kmworks.dsltools.adt.ml;

import kmworks.dsltools.adt.base.Multiplicity;
import org.immutables.value.Value;

/**
 * Created by cpl on 18.03.2017.
 */
@Value.Immutable
public abstract class Multiple extends Predicable {
    @Value.Parameter public abstract Multiplicity multiplicity();
    @Value.Parameter public abstract Primary primary();
}
