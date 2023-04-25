package com.wentong.seq;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public interface Seq<T> {

    void consume(Consumer<T> consumer);

    static <T> Seq<T> unit(T t) {
        return c -> c.accept(t);
    }

    default <E> Seq<E> map(Function<T, E> f) {
        return c -> consume(t -> c.accept(f.apply(t)));
    }

    default <E> Seq<E> flatMap(Function<T, Seq<E>> f) {
        return c -> consume(t -> f.apply(t).consume(c));
    }

    default Seq<T> filter(Predicate<T> predicate) {
        return c -> consume(t -> {
            if (predicate.test(t)) {
                c.accept(t);
            }
        });
    }

}
