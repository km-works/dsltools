package kmworks.dsltools.ast.ml;

import kmworks.dsltools.ast.base.Multiplicity;
import org.immutables.value.Value;

/**
 * Created by cpl on 18.03.2017.
 */
@Value.Immutable
public abstract class Multiple extends Predicable {
    @Value.Parameter public abstract Multiplicity multiplicity();
    @Value.Parameter public abstract Primary primary();
}
