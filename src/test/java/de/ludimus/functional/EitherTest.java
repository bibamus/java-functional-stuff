package de.ludimus.functional;

import org.junit.jupiter.api.Test;

import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

class EitherTest {

    @Test
    void testLeftCreationAndValueRetrieval() {
        Either<String, Integer> either = Either.left("Error");
        assertTrue(either instanceof Left);
        assertEquals("Error", either.fold(Function.identity(), Function.identity()));
    }

    @Test
    void testRightCreationAndValueRetrieval() {
        Either<String, Integer> either = Either.right(10);
        assertTrue(either instanceof Right);
        assertEquals(10, either.fold(Function.identity(), Function.identity()));
    }

    @Test
    void testMapRightOnRight() {
        Either<String, Integer> right = Either.right(5);
        Either<String, Integer> mapped = right.map(r -> r * 2);
        assertEquals(10, mapped.getOrElse(0));
    }

    @Test
    void testMapRightOnLeft() {
        Either<String, Integer> left = Either.left("Error");
        Either<String, Integer> mapped = left.map(r -> r * 2);
        assertEquals("Error", mapped.fold(Function.identity(), r -> "No Error"));
    }

    @Test
    void testMapLeftOnLeft() {
        Either<String, Integer> left = Either.left("Error");
        Either<String, Integer> mapped = left.mapLeft(l -> l + " Modified");
        assertEquals("Error Modified", mapped.fold(Function.identity(), r -> "No Error"));
    }

    @Test
    void testMapLeftOnRight() {
        Either<String, Integer> right = Either.right(10);
        Either<String, Integer> mapped = right.mapLeft(l -> l + " Modified");
        assertEquals(Integer.valueOf(10), mapped.getOrElse(0));
    }

    @Test
    void testFlatMapRightOnRight() {
        Either<String, Integer> right = Either.right(5);
        Either<String, Integer> flatMapped = right.flatMapRight(r -> Either.right(r * 2));
        assertEquals(10, flatMapped.getOrElse(0));
    }

    @Test
    void testFlatMapRightOnLeft() {
        Either<String, Integer> left = Either.left("Error");
        Either<String, Integer> flatMapped = left.flatMapRight(r -> Either.right(r * 2));
        assertEquals("Error", flatMapped.fold(Function.identity(), r -> "No Error"));
    }

    @Test
    void testFlatMapLeftOnLeft() {
        Either<String, Integer> left = Either.left("Error");
        Either<String, Integer> flatMapped = left.flatMapLeft(l -> Either.left(l + " Modified"));
        assertEquals("Error Modified", flatMapped.fold(Function.identity(), r -> "No Error"));
    }

    @Test
    void testFlatMapLeftOnRight() {
        Either<String, Integer> right = Either.right(10);
        Either<String, Integer> flatMapped = right.flatMapLeft(l -> Either.left(l + " Modified"));
        assertEquals(Integer.valueOf(10), flatMapped.getOrElse(0));
    }

    @Test
    void testGetOrElseFromLeft() {
        Either<String, Integer> left = Either.left("Error");
        assertEquals(Integer.valueOf(0), left.getOrElse(0));
    }

    @Test
    void testGetOrElseFromRight() {
        Either<String, Integer> right = Either.right(10);
        assertEquals(Integer.valueOf(10), right.getOrElse(0));
    }

    @Test
    void testFoldFromLeft() {
        Either<String, Integer> left = Either.left("Error");
        String result = left.fold(l -> "Left: " + l, r -> "Right: " + r);
        assertEquals("Left: Error", result);
    }

    @Test
    void testFoldFromRight() {
        Either<String, Integer> right = Either.right(10);
        String result = right.fold(l -> "Left: " + l, r -> "Right: " + r);
        assertEquals("Right: 10", result);
    }

    @Test
    void testPatternMatching(){
        Either<String, Integer> right = Either.right(10);

        switch (right){
            case Left l -> fail();
            case Right(Integer value)-> assertEquals(10,value);
        }

        Either<String, Integer> left = Either.left("Error");

        switch (left){
            case Left(String value) -> assertEquals("Error", value);
            case Right r -> fail();
        }
    }


    @Test
    void testIsLeftForLeftInstance() {
        Either<String, Integer> left = Either.left("Error");
        assertTrue(left.isLeft(), "Expected left to be identified as Left instance.");
        assertFalse(left.isRight(), "Left instance should not be identified as Right.");
    }

    @Test
    void testIsLeftForRightInstance() {
        Either<String, Integer> right = Either.right(10);
        assertTrue(right.isRight(), "Expected right to be identified as Right instance.");
        assertFalse(right.isLeft(), "Right instance should not be identified as Left.");
    }

    @Test
    void testToOptionalWithRight() {
        Either<String, Integer> right = Either.right(10);
        assertTrue(right.toOptional().isPresent(), "Expected Optional to be present for Right.");
        assertEquals(10, right.toOptional().orElseThrow());
    }

    @Test
    void testToOptionalWithLeft() {
        Either<String, Integer> left = Either.left("Error");
        assertFalse(left.toOptional().isPresent(), "Expected Optional to be empty for Left.");
    }

    @Test
    void testToStreamWithRight() {
        Either<String, Integer> right = Either.right(10);
        assertEquals(1, right.toStream().count(), "Expected Stream to contain one element for Right.");
        assertEquals(10, right.toStream().findFirst().orElseThrow());
    }

    @Test
    void testToStreamWithLeft() {
        Either<String, Integer> left = Either.left("Error");
        assertEquals(0, left.toStream().count(), "Expected Stream to be empty for Left.");
    }
    @Test
    void testToOptionWithRight() {
        Either<String, Integer> right = Either.right(10);
        Option<Integer> option = right.toOption();
        assertTrue(option instanceof Some, "Expected Option to be Some for Right.");
        option.ifPresent(value -> assertEquals(10, value, "Some value did not match expected."));
    }

    @Test
    void testToOptionWithLeft() {
        Either<String, Integer> left = Either.left("Error");
        Option<Integer> option = left.toOption();
        assertTrue(option instanceof None, "Expected Option to be None for Left.");
    }
}
