package kmworks.dsltools.adt.ml;

import org.immutables.value.Value;

/**
 * Created by cpl on 18.03.2017.
 */
@Value.Immutable
public abstract class NonTerminal extends Primary {
    @Value.Parameter public abstract String name();
}
