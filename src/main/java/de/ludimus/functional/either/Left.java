package de.ludimus.functional.either;

import de.ludimus.functional.option.None;
import de.ludimus.functional.option.Option;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

public record Left<L, R>(L value) implements Either<L, R> {
    public Left {
        Objects.requireNonNull(value);
    }

    @Override
    public boolean isLeft() {
        return true;
    }

    @Override
    public boolean isRight() {
        return false;
    }

    @Override
    public <R2> Either<L, R2> mapRight(Function<? super R, ? extends R2> mapper) {
        return new Left<>(value);
    }

    @Override
    public <L2> Either<L2, R> mapLeft(Function<? super L, ? extends L2> mapper) {
        return new Left<>(mapper.apply(value));
    }

    @Override
    public <R2> Either<L, R2> flatMapRight(Function<? super R, Either<L, R2>> mapper) {
        return new Left<>(value);
    }

    @Override
    public <L2> Either<L2, R> flatMapLeft(Function<? super L, Either<L2, R>> mapper) {
        return mapper.apply(value);
    }

    @Override
    public R getOrElse(R other) {
        return other;
    }

    @Override
    public <T> T fold(Function<? super L, ? extends T> leftMapper, Function<? super R, ? extends T> rightMapper) {
        return leftMapper.apply(value);
    }

    @Override
    public Optional<R> toOptional() {
        return Optional.empty();
    }

    @Override
    public Stream<R> toStream() {
        return Stream.empty();
    }

    @Override
    public Option<R> toOption() {
        return new None<>();
    }
}
