package org.alenapech;

import org.alenapech.exception.IllegalCollectionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.whenNew;

class NumberComparatorTest implements Constants {

    NumberComparator numberComparator;

    @BeforeEach
    void setUp() {
        numberComparator = new NumberComparator();
    }

    /**
     * Sunny-Day Unit test with mocked NumberCollection
     */
    @ParameterizedTest
    @CsvSource({"1, 2, " + LESS_AVERAGE_VALUE_DESIGNED_MESSAGE
            , "2, 1, " + GREATER_AVERAGE_VALUE_DESIGNED_MESSAGE
            , "2, 2, " + EQUAL_AVERAGE_VALUE_DESIGNED_MESSAGE})
    void sunnyDayCompare(int collectionNumber1, int collectionNumber2, String expectedResult) throws Exception {
        NumberCollection lessNumbers = mock(NumberCollection.class);
        NumberCollection greaterNumbers = mock(NumberCollection.class);

        when(lessNumbers.compareTo(any(NumberCollection.class))).thenReturn(-1);
        when(greaterNumbers.compareTo(any(NumberCollection.class))).thenReturn(1);
        when(greaterNumbers.compareTo(greaterNumbers)).thenReturn(0);

        whenNew(NumberCollection.class).withArguments(Arrays.asList(1)).thenReturn(lessNumbers);
        whenNew(NumberCollection.class).withArguments(Arrays.asList(2)).thenReturn(greaterNumbers);

        assertEquals(expectedResult, numberComparator.compare(Arrays.asList(collectionNumber1)
                , Arrays.asList(collectionNumber2)));
    }

    /**
     * Rainy-Day Unit test with mocked NumberCollection
     */
    @Test
    void rainyDayCompare() throws Exception {
        NumberCollection numbers = mock(NumberCollection.class);
        when(numbers.compareTo(any(NumberCollection.class))).thenReturn(-1);
        whenNew(NumberCollection.class).withArguments(anyCollection()).thenReturn(numbers);
        Collection<Number> collection =  Arrays.asList(1);

        assertThrows(IllegalCollectionException.class, () -> numberComparator.compare(null, collection));
        assertThrows(IllegalCollectionException.class, () -> numberComparator.compare(null, collection));
        verify(numbers, times(0)).compareTo(any(NumberCollection.class));
    }

    private static Stream<Arguments> integrationSunnyDayCompareArgs() {
        return Stream.of(
                Arguments.of(Arrays.asList(1, 2.5, 3), Arrays.asList(1, 2.4, 3), GREATER_AVERAGE_VALUE_DESIGNED_MESSAGE),
                Arguments.of(Arrays.asList(1, 2.5, 3), Arrays.asList(1, 2.6, 3), LESS_AVERAGE_VALUE_DESIGNED_MESSAGE),
                Arguments.of(Arrays.asList(1, 2.6, 3), Arrays.asList(1, 2.6, 3), EQUAL_AVERAGE_VALUE_DESIGNED_MESSAGE)
        );
    }

    /**
     * Integration test with real NumberCollection
     */
    @ParameterizedTest
    @MethodSource("integrationSunnyDayCompareArgs")
    void integrationSunnyDayCompare(Collection<Number> o1, Collection<Number> o2, String expectedResult) {
        assertEquals(expectedResult, numberComparator.compare(o1, o2));
    }
}