package kmworks.dsltools.ast.ml;


import kmworks.dsltools.ast.base.ASTNode;
import kmworks.dsltools.ast.base.Seq;
import org.immutables.value.Value;

/**
 * Created by cpl on 18.03.2017.
 */
@Value.Immutable
public abstract class Grammar extends ASTNode {
    @Value.Parameter public abstract Seq<Production> productions();
}

