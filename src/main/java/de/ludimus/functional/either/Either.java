package de.ludimus.functional.either;

import de.ludimus.functional.option.Option;

import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * A sealed interface representing a value of one of two possible types, Left or Right.
 * Either is often used to represent a value which is either correct or an error;
 * by convention, Left is used for failure and Right is used for success.
 *
 * @param <L> the type of Left value
 * @param <R> the type of Right value
 */
public sealed interface Either<L, R> permits Left, Right {

    /**
     * Creates an Either instance representing a Left value.
     *
     * @param value the Left value
     * @param <L>   the type of the Left value
     * @param <R>   the type of the Right value
     * @return an Either instance containing a Left value
     */
    static <L, R> Either<L, R> left(L value) {
        return new Left<>(value);
    }

    /**
     * Creates an Either instance representing a Right value.
     *
     * @param value the Right value
     * @param <L>   the type of the Left value
     * @param <R>   the type of the Right value
     * @return an Either instance containing a Right value
     */
    static <L, R> Either<L, R> right(R value) {
        return new Right<>(value);
    }

    /**
     * Checks if this instance is a Left value.
     *
     * @return true if this is a Left value, false otherwise
     */
    boolean isLeft();

    /**
     * Checks if this instance is a Right value.
     *
     * @return true if this is a Right value, false otherwise
     */
    boolean isRight();

    /**
     * Maps the Right value of this Either by applying a function to it.
     *
     * @param mapper the function to apply to the Right value
     * @param <R2>   the type of the resulting Right value
     * @return a new Either instance with the Right value transformed
     */
    <R2> Either<L, R2> mapRight(Function<? super R, ? extends R2> mapper);

    /**
     * Maps the Left value of this Either by applying a function to it.
     *
     * @param mapper the function to apply to the Left value
     * @param <L2>   the type of the resulting Left value
     * @return a new Either instance with the Left value transformed
     */
    <L2> Either<L2, R> mapLeft(Function<? super L, ? extends L2> mapper);


    /**
     * Transforms the Right value of this Either to another Either by applying a function to it.
     * Useful for chaining operations that return Either.
     *
     * @param mapper the function to apply to the Right value
     * @param <R2>   the type of the Right value in the new Either
     * @return a new Either instance resulting from the transformation
     */
    <R2> Either<L, R2> flatMapRight(Function<? super R, Either<L, R2>> mapper);

    /**
     * Transforms the Left value of this Either to another Either by applying a function to it.
     * Useful for chaining operations that might change the type of the Left value.
     *
     * @param mapper the function to apply to the Left value
     * @param <L2>   the type of the Left value in the new Either
     * @return a new Either instance resulting from the transformation
     */
    <L2> Either<L2, R> flatMapLeft(Function<? super L, Either<L2, R>> mapper);

    /**
     * Returns the Right value or a default value if this is a Left.
     *
     * @param other the default value to return if this is a Left
     * @return the Right value if this is Right, or the default value if this is Left
     */
    R getOrElse(R other);

    /**
     * Applies a function to the value inside this Either, whether it is a Left or a Right.
     * This method allows you to transform an Either<L, R> into a single value of type T.
     *
     * @param leftMapper  the function to apply if this is a Left
     * @param rightMapper the function to apply if this is a Right
     * @param <T>         the type of the result of the functions
     * @return the result of applying the appropriate function to the value inside this Either
     */
    <T> T fold(Function<? super L, ? extends T> leftMapper, Function<? super R, ? extends T> rightMapper);

    /**
     * Converts this Either to an {@link Optional}, containing the Right value if it exists, or empty if this is a Left.
     *
     * @return an Optional containing the Right value if present, otherwise an empty Optional
     */
    Optional<R> toOptional();

    /**
     * Converts this Either to a {@link Stream}, containing the Right value if it exists, or an empty Stream if this is a Left.
     *
     * @return a Stream containing the Right value if present, otherwise an empty Stream
     */
    Stream<R> toStream();

    /**
     * Converts this Either to an {@link Option}, containing the Right value if it exists, or None if this is a Left.
     *
     * @return an Option containing the Right value if present, otherwise None
     */
    Option<R> toOption();
}

