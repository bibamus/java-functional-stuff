package de.ludimus.functional.option;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

public record Some<T>(T value) implements Option<T> {

    public Some {
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
