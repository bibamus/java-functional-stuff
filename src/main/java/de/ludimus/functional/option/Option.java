package de.ludimus.functional.option;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

public sealed interface Option<T> permits Some, None {

    static <T> Option<T> of(T value) {
        return value != null ? new Some<>(value) : new None<>();
    }

    static <T> Option<T> none() {
        return new None<>();
    }

    boolean isPresent();

    boolean isEmpty();

    T get();

    void ifPresent(Consumer<? super T> action);

    <U> Option<U> map(Function<? super T, ? extends U> mapper);

    <U> Option<U> flatMap(Function<? super T, Option<U>> mapper);

    T orElse(T other);

    T orElseGet(Supplier<? extends T> supplier);

    Stream<T> stream();
}

