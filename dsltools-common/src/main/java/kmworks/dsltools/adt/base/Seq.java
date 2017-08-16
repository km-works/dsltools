package kmworks.dsltools.adt.base;

import com.google.common.collect.ImmutableList;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.immutables.value.Value;
import xtc.util.Pair;

/**
 * Created by cpl on 21.03.2017.
 */
@Value.Immutable
public abstract class Seq<T> {
    
    @Value.Parameter public abstract List<T> list();
    
    public static <E> Seq<E> of() {
        return SeqImpl.of(Collections.EMPTY_LIST);
    }

    public static <E> Seq<E> of(@Nonnull E... elements) {
        return SeqImpl.of(ImmutableList.copyOf(elements));
    }
    
    public static <E> Seq<E> of(@Nonnull E head, @Nullable Pair<E> tail) {
        return of(new Pair(head, tail == null ? Pair.EMPTY : tail));
    }

    public static <E> Seq<E> of(Pair<E> pair) {
        if (pair == null || pair.isEmpty()) {
            return Seq.of();
        } else {
            return SeqImpl.of(pair.list());
        }
    }

}
