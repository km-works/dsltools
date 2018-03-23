package kmworks.dsltools.adt.adt;

import kmworks.dsltools.adt.base.AbstractRoot;
import kmworks.dsltools.adt.base.Seq;
import org.immutables.value.Value;

/**
 * Created by cpl on 21.03.2017.
 */
@Value.Immutable
public abstract class ADT extends AbstractRoot {

    @Value.Parameter
    public abstract String name();

    @Value.Parameter
    public abstract Seq<TypeDef> main();

    @Value.Parameter
    public abstract Seq<TypeDef> aux();

}
