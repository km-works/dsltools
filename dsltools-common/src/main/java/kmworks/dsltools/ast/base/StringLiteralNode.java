package kmworks.dsltools.ast.base;

import org.immutables.value.Value;

@Value.Immutable
public abstract class StringLiteralNode extends ASTNode {
    @Value.Parameter public abstract String parsedString();
}
