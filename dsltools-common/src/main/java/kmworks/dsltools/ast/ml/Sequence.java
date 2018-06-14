package kmworks.dsltools.ast.ml;

import kmworks.dsltools.ast.base.ASTNode;
import org.immutables.value.Value;

import kmworks.dsltools.ast.base.Seq;

/**
 * Created by cpl on 18.03.2017.
 */
@Value.Immutable
public abstract class Sequence extends ASTNode {
    @Value.Parameter public abstract Seq<Term> terms();
}

