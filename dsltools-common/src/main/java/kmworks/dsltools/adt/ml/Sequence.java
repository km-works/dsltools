package kmworks.dsltools.adt.ml;

import kmworks.dsltools.adt.base.AbstractRoot;
import org.immutables.value.Value;

import kmworks.dsltools.adt.base.Seq;

/**
 * Created by cpl on 18.03.2017.
 */
@Value.Immutable
public abstract class Sequence extends AbstractRoot {
    @Value.Parameter public abstract Seq<Term> terms();
}

