package de.ludimus.functional;

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

record Some<T>(T value) implements Option<T> {

    Some {
        if (value == null) {
            throw new NullPointerException("value cannot be null");
        }
    }

    @Override
    public boolean isPresent() {
        return true;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public T get() {
        return value;
    }

    @Override
    public void ifPresent(Consumer<? super T> action) {
        action.accept(value);
    }

    @Override
    public <U> Option<U> map(Function<? super T, ? extends U> mapper) {
        return Option.of(mapper.apply(value));
    }

    @Override
    public <U> Option<U> flatMap(Function<? super T, Option<U>> mapper) {
        return mapper.apply(value);
    }

    @Override
    public T orElse(T other) {
        return value;
    }

    @Override
    public T orElseGet(Supplier<? extends T> supplier) {
        return value;
    }

    @Override
    public Stream<T> stream() {
        return Stream.of(value);
    }
}

record None<T>() implements Option<T> {

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