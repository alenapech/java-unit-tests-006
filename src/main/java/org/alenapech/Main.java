package org.alenapech;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {

    private static final String ILLEGAL_COLLECTION_EXCEPTION_MESSAGE = "Проверьте входные данные (поддерживаются только числа)";
    private static final String FIRST_COLLECTION_INPUT_MESSAGE = "Введите первую коллекцию чисел через пробел";
    private static final String SECOND_COLLECTION_INPUT_MESSAGE = "Введите вторую коллекцию чисел через пробел";

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        try {
            System.out.println(FIRST_COLLECTION_INPUT_MESSAGE);
            List<Number> numbers1 = parseNumbers(in);
            System.out.println(SECOND_COLLECTION_INPUT_MESSAGE);
            List<Number> numbers2 = parseNumbers(in);
            NumberComparator numberComparator = new NumberComparator();
            System.out.println(numberComparator.compare(numbers1, numbers2));
        } catch (NumberFormatException e) {
            System.err.println(ILLEGAL_COLLECTION_EXCEPTION_MESSAGE);
        }
    }

    private static List<Number> parseNumbers(Scanner in) {
        return Arrays.stream(in.nextLine().split(" "))
                .map(s -> s.replace(",", "."))
                .map(Double::parseDouble)
                .collect(Collectors.toList());
    }
}