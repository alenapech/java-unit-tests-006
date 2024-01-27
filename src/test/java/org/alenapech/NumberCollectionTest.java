package org.alenapech;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class NumberCollectionTest {

    private static Stream<Arguments> sunnyDayCompareToArgs() {
        return Stream.of(
                Arguments.of(new NumberCollection(Arrays.asList(1, 2.5, 3))
                        , new NumberCollection(Arrays.asList(1, 2.4, 3))
                        , 1),
                Arguments.of(new NumberCollection(Arrays.asList(1, 2.5, 3))
                        , new NumberCollection(Arrays.asList(1, 2.6, 3))
                        , -1),
                Arguments.of(new NumberCollection(Arrays.asList(1, 2.6, 3))
                        , new NumberCollection(Arrays.asList(1, 2.6, 3))
                        , 0),
                Arguments.of(new NumberCollection(Arrays.asList(1,-1))
                        , new NumberCollection(Collections.emptyList())
                        , 0)
        );
    }
    @ParameterizedTest
    @MethodSource("sunnyDayCompareToArgs")
    void sunnyDayCompareTo(NumberCollection o1, NumberCollection o2, int expectedResult) {
        assertEquals(expectedResult, o1.compareTo(o2));
    }

    /**
     * Rainy-Day Unit test
     */
    @Test
    void rainyDayCompareTo() {
        NumberCollection numberCollection = new NumberCollection(Arrays.asList(1, 2, 3));
        assertThrows(NullPointerException.class, () -> numberCollection.compareTo(null));
    }

    /**
     * Rainy-Day Unit test
     */
    @Test
    void rainyDayConstructor() {
        assertThrows(NullPointerException.class, () -> new NumberCollection(null));
    }

    private static Stream<Arguments> testEqualsToArgs() {
        NumberCollection NumberCollection = new NumberCollection(Collections.emptyList());
        return Stream.of(
                Arguments.of(new NumberCollection(Arrays.asList(1, 2.5, 3))
                        , new NumberCollection(Arrays.asList(1, 2.4, 3))
                        , false),
                Arguments.of(new NumberCollection(Arrays.asList(1, 2.5, 3))
                        , new NumberCollection(Arrays.asList(1, 2.5, 3))
                        , true),
                Arguments.of(new NumberCollection(Arrays.asList(1, 2.6, 3))
                        , new NumberCollection(Arrays.asList(2.6, 3, 1))
                        , true),
                Arguments.of(new NumberCollection(Arrays.asList(1, 2.6, 3))
                        , null
                        , false),
                Arguments.of(new NumberCollection(Arrays.asList(1, 2.6, 3))
                        , ""
                        , false),
                Arguments.of(NumberCollection
                        , NumberCollection
                        , true),
                Arguments.of(NumberCollection
                        , new NumberCollection(Arrays.asList(1, -1))
                        , true)
        );
    }

    @ParameterizedTest
    @MethodSource("testEqualsToArgs")
    void testEquals(NumberCollection o1, Object o2, boolean expectedResult) {
        assertEquals(expectedResult, o1.equals(o2));
    }

    private static Stream<Arguments> testHashCodeToArgs() {
        return Stream.of(
                Arguments.of(new NumberCollection(Arrays.asList(1, 2.5, 3))
                        , 357826560),
                Arguments.of(new NumberCollection(Collections.emptyList())
                        , 0),
                Arguments.of(new NumberCollection(Arrays.asList(1, 2.6, 3))
                        ,-644349952),
                Arguments.of(new NumberCollection(Arrays.asList(3, 1, 2.6))
                        ,-644349952)
        );
    }

    @ParameterizedTest
    @MethodSource("testHashCodeToArgs")
    void testHashCode(NumberCollection o, int expectedResult) {
        assertEquals(expectedResult, o.hashCode());
    }
}