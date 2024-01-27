package org.alenapech;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemErrAndOut;
import static com.github.stefanbirkner.systemlambda.SystemLambda.withTextFromSystemIn;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MainTest implements Constants {

    private static Stream<Arguments> integrationMainArgs() {
        return Stream.of(
                Arguments.of("1 2"
                        , "3 4"
                        , FIRST_COLLECTION_INPUT_DESIGNED_MESSAGE + System.lineSeparator()
                            + SECOND_COLLECTION_INPUT_DESIGNED_MESSAGE + System.lineSeparator()
                            + LESS_AVERAGE_VALUE_DESIGNED_MESSAGE),
                Arguments.of("1.1 1,2"
                        , "0,1 0.12"
                        , FIRST_COLLECTION_INPUT_DESIGNED_MESSAGE + System.lineSeparator()
                            + SECOND_COLLECTION_INPUT_DESIGNED_MESSAGE + System.lineSeparator()
                            + GREATER_AVERAGE_VALUE_DESIGNED_MESSAGE),
                Arguments.of("1 2.1 3"
                        , "2.1 3 1"
                        , FIRST_COLLECTION_INPUT_DESIGNED_MESSAGE + System.lineSeparator()
                            + SECOND_COLLECTION_INPUT_DESIGNED_MESSAGE + System.lineSeparator()
                            + EQUAL_AVERAGE_VALUE_DESIGNED_MESSAGE),
                Arguments.of("1 2"
                        , ""
                        , FIRST_COLLECTION_INPUT_DESIGNED_MESSAGE + System.lineSeparator()
                            + SECOND_COLLECTION_INPUT_DESIGNED_MESSAGE + System.lineSeparator()
                            + ILLEGAL_COLLECTION_EXCEPTION_DESIGNED_MESSAGE),
                Arguments.of("1 2"
                        , "asdasd"
                        , FIRST_COLLECTION_INPUT_DESIGNED_MESSAGE + System.lineSeparator()
                            + SECOND_COLLECTION_INPUT_DESIGNED_MESSAGE + System.lineSeparator()
                            + ILLEGAL_COLLECTION_EXCEPTION_DESIGNED_MESSAGE),
                Arguments.of(""
                        , ""
                        , FIRST_COLLECTION_INPUT_DESIGNED_MESSAGE + System.lineSeparator()
                            + ILLEGAL_COLLECTION_EXCEPTION_DESIGNED_MESSAGE),
                Arguments.of("asd"
                        , "12"
                        , FIRST_COLLECTION_INPUT_DESIGNED_MESSAGE + System.lineSeparator()
                            + ILLEGAL_COLLECTION_EXCEPTION_DESIGNED_MESSAGE)
        );
    }

    /**
     * Integration test with real NumberCollection, NumberComparator
     */
    @ParameterizedTest
    @MethodSource("integrationMainArgs")
    void integrationMain(String numbers1, String numbers2, String expectedResult) throws Exception {
        withTextFromSystemIn(numbers1, numbers2).execute(() -> {
            String actualResult = tapSystemErrAndOut(() -> {
                Main.main(new String[]{});
            });
            assertEquals(expectedResult, actualResult.trim());
        });
    }

    private static Stream<Arguments> sunnyDayMainArgs() {
        return Stream.of(
                Arguments.of("1 2"
                        , "3 4"
                        , LESS_AVERAGE_VALUE_DESIGNED_MESSAGE
                        , FIRST_COLLECTION_INPUT_DESIGNED_MESSAGE + System.lineSeparator()
                                + SECOND_COLLECTION_INPUT_DESIGNED_MESSAGE + System.lineSeparator()
                                + LESS_AVERAGE_VALUE_DESIGNED_MESSAGE),
                Arguments.of("1.1 1,2"
                        , "0,1 0.12"
                        , GREATER_AVERAGE_VALUE_DESIGNED_MESSAGE
                        , FIRST_COLLECTION_INPUT_DESIGNED_MESSAGE + System.lineSeparator()
                                + SECOND_COLLECTION_INPUT_DESIGNED_MESSAGE + System.lineSeparator()
                                + GREATER_AVERAGE_VALUE_DESIGNED_MESSAGE),
                Arguments.of("1 2.1 3"
                        , "2.1 3 1"
                        , EQUAL_AVERAGE_VALUE_DESIGNED_MESSAGE
                        , FIRST_COLLECTION_INPUT_DESIGNED_MESSAGE + System.lineSeparator()
                                + SECOND_COLLECTION_INPUT_DESIGNED_MESSAGE + System.lineSeparator()
                                + EQUAL_AVERAGE_VALUE_DESIGNED_MESSAGE)
        );
    }

    /**
     * Sunny Day Unit test with mocked NumberComparator
     */
    @ParameterizedTest
    @MethodSource("sunnyDayMainArgs")
    void main(String numbers1, String numbers2, String numberComparatorResponse, String expectedResult) throws Exception {
        NumberComparator numberComparator = mock(NumberComparator.class);
        when(numberComparator.compare(new NumberCollection(parseNumbers(numbers1)), new NumberCollection(parseNumbers(numbers2))))
                .thenReturn(numberComparatorResponse);

        withTextFromSystemIn(numbers1, numbers2).execute(() -> {
            String actualResult = tapSystemErrAndOut(() -> {
                Main.main(new String[]{});
            });
            assertEquals(expectedResult, actualResult.trim());
        });
    }

    private static List<Number> parseNumbers(String in) {
        return Arrays.stream(in.split(" "))
                .map(s -> s.replace(",", "."))
                .map(Double::parseDouble)
                .collect(Collectors.toList());
    }
}