package de.ludimus.functional.either;

import de.ludimus.functional.option.Option;
import de.ludimus.functional.option.Some;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

public record Right<L, R>(R value) implements Either<L, R> {
    public Right {
        Objects.requireNonNull(value);
    }

    @Override
    public boolean isLeft() {
        return false;
    }

    @Override
    public boolean isRight() {
        return true;
    }

    @Override
    public <R2> Either<L, R2> mapRight(Function<? super R, ? extends R2> mapper) {
        return new Right<>(mapper.apply(value));
    }

    @Override
    public <L2> Either<L2, R> mapLeft(Function<? super L, ? extends L2> mapper) {
        return new Right<>(value);
    }

    @Override
    public <R2> Either<L, R2> flatMapRight(Function<? super R, Either<L, R2>> mapper) {
        return mapper.apply(value);
    }

    @Override
    public <L2> Either<L2, R> flatMapLeft(Function<? super L, Either<L2, R>> mapper) {
        return new Right<>(value);
    }

    @Override
    public R getOrElse(R other) {
        return value;
    }

    @Override
    public <T> T fold(Function<? super L, ? extends T> leftMapper, Function<? super R, ? extends T> rightMapper) {
        return rightMapper.apply(value);
    }

    @Override
    public Optional<R> toOptional() {
        return Optional.of(value);
    }

    @Override
    public Stream<R> toStream() {
        return Stream.of(value);
    }

    @Override
    public Option<R> toOption() {
        return new Some<>(value);
    }
}
