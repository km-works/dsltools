package kmworks.dsltools.adt.ml;

import kmworks.dsltools.adt.base.*;
import org.immutables.value.Value;

/**
 * Created by cpl on 18.03.2017.
 */
@Value.Immutable
public abstract class Choice extends Primary {
    @Value.Parameter public abstract boolean epsilon();
    @Value.Parameter public abstract Seq<Sequence> sequences();
}

