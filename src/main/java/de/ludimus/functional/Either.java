package de.ludimus.functional;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

public sealed interface Either<L, R> permits Left, Right {

    static <L, R> Either<L, R> left(L value) {
        return new Left<>(value);
    }

    static <L, R> Either<L, R> right(R value) {
        return new Right<>(value);
    }

    boolean isLeft();

    boolean isRight();

    default <R2> Either<L, R2> map(Function<? super R, ? extends R2> mapper) {
        return mapRight(mapper);
    }

    <R2> Either<L, R2> mapRight(Function<? super R, ? extends R2> mapper);

    <L2> Either<L2, R> mapLeft(Function<? super L, ? extends L2> mapper);

    <R2> Either<L, R2> flatMapRight(Function<? super R, Either<L, R2>> mapper);

    <L2> Either<L2, R> flatMapLeft(Function<? super L, Either<L2, R>> mapper);

    R getOrElse(R other);

    <T> T fold(Function<? super L, ? extends T> leftMapper, Function<? super R, ? extends T> rightMapper);

    Optional<R> toOptional();

    Stream<R> toStream();


    Option<R> toOption();
}

record Left<L, R>(L value) implements Either<L, R> {
    Left {
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

record Right<L, R>(R value) implements Either<L, R> {
    Right {
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