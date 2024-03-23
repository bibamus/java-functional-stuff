package de.ludimus.functional;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class OptionTest {

    @Test
    void testSomeHoldsValue() {
        Option<String> some = new Some<>("Hello");
        assertTrue(some.isPresent());
        assertFalse(some.isEmpty());
        assertEquals("Hello", some.get());
    }

    @Test
    void testNoneHoldsNoValue() {
        Option<String> none = new None<>();
        assertFalse(none.isPresent());
        assertTrue(none.isEmpty());
        assertThrows(UnsupportedOperationException.class, none::get);
    }

    @Test
    void testIfPresent() {
        Option<String> some = new Some<>("Hello");
        some.ifPresent(value -> assertEquals("Hello", value));

        Option<String> none = new None<>();
        none.ifPresent(value -> fail("Should not be called"));
    }

    @Test
    void testMap() {
        Option<String> some = new Some<>("Hello");
        Option<Integer> mappedSome = some.map(String::length);
        assertEquals(5, mappedSome.get());

        Option<String> none = new None<>();
        Option<Integer> mappedNone = none.map(String::length);
        assertTrue(mappedNone.isEmpty());
    }

    @Test
    void testFlatMap() {
        Option<String> some = new Some<>("Hello");
        Option<Integer> flatMappedSome = some.flatMap(s -> new Some<>(s.length()));
        assertEquals(5, flatMappedSome.get());

        Option<String> none = new None<>();
        Option<Integer> flatMappedNone = none.flatMap(s -> new Some<>(s.length()));
        assertTrue(flatMappedNone.isEmpty());
    }

    @Test
    void testOrElse() {
        Option<String> some = new Some<>("Hello");
        assertEquals("Hello", some.orElse("World"));

        Option<String> none = new None<>();
        assertEquals("World", none.orElse("World"));
    }

    @Test
    void testOrElseGet() {
        Option<String> some = new Some<>("Hello");
        assertEquals("Hello", some.orElseGet(() -> "World"));

        Option<String> none = new None<>();
        assertEquals("World", none.orElseGet(() -> "World"));
    }

    @Test
    void testStream() {
        Option<String> some = new Some<>("Hello");
        assertEquals(1, some.stream().count());
        assertEquals("Hello", some.stream().findFirst().orElseThrow());

        Option<String> none = new None<>();
        assertEquals(0, none.stream().count());
    }

    @Test
    void testPatternMatching() {
        Option<String> some = new Some<>("Hello, Java 21!");
        Option<String> none = Option.none();

        // Test pattern matching with Some
        switch (some) {
            case Some(String value) -> assertEquals("Hello, Java 21!", value, "Some did not match expected value.");
            case None() -> fail("Some expected, but matched None.");
        }

        // Test pattern matching with None
        switch (none) {
            case Some(String ignored) -> fail("None expected, but matched Some.");
            case None() -> assertTrue(true, "Correctly matched None."); // This is expected
        }
    }
}
