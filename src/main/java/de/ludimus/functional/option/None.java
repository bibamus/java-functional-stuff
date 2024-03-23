package de.ludimus.functional.option;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

public record None<T>() implements Option<T> {

    @Override
    public boolean isPresent() {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public T get() {
        throw new UnsupportedOperationException("No value present");
    }

    @Override
    public void ifPresent(Consumer<? super T> action) {
        // Do nothing
    }

    @Override
    public <U> Option<U> map(Function<? super T, ? extends U> mapper) {
        return new None<>();
    }

    @Override
    public <U> Option<U> flatMap(Function<? super T, Option<U>> mapper) {
        return new None<>();
    }

    @Override
    public T orElse(T other) {
        return other;
    }

    @Override
    public T orElseGet(Supplier<? extends T> supplier) {
        return supplier.get();
    }

    @Override
    public Stream<T> stream() {
        return Stream.empty();
    }
}
