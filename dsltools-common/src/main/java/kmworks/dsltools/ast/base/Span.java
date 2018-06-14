package kmworks.dsltools.ast.base;

import org.immutables.value.Value;

import javax.annotation.Nonnull;

@Value.Immutable
public abstract class Span {
    @Value.Parameter public abstract int first();
    @Value.Parameter public abstract int last();

    public int length() {
        return last() - first() + 1;
    }

    public static Span withoutTrailingWS(@Nonnull final Span span, char[] source) {
        int start = span.first();
        int end = span.last();
        int i = end >= source.length ? source.length - 1 : end;
        while (i >= start && Character.isWhitespace(source[i])) {
            i--;
        }
        return SpanImpl.of(start, i < start ? start : i);
    }
}
