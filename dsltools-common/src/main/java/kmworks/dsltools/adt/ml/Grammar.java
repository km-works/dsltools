package kmworks.dsltools.adt.ml;


import kmworks.dsltools.adt.base.AbstractRoot;
import kmworks.dsltools.adt.base.Seq;
import org.immutables.value.Value;

/**
 * Created by cpl on 18.03.2017.
 */
@Value.Immutable
public abstract class Grammar extends AbstractRoot {
    @Value.Parameter public abstract Seq<Production> productions();
}

