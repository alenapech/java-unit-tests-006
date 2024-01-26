package org.alenapech;

import org.alenapech.exception.IllegalCollectionException;

import java.util.Collection;

public class NumberComparator implements Comparator<Number, String> {

    private static final String GREATER_AVERAGE_VALUE_MESSAGE = "Первый список имеет большее среднее значение";
    private static final String LESS_AVERAGE_VALUE_MESSAGE = "Второй список имеет большее среднее значение";
    private static final String EQUAL_AVERAGE_VALUE_MESSAGE = "Средние значения равны";
    private static final String ILLEGAL_COLLECTION_EXCEPTION_MESSAGE = "Проверьте входные коллекции";

    @Override
    public String compare(Collection<Number> o1, Collection<Number> o2) {
        if (o1 == null || o2 == null) {
            throw new IllegalCollectionException(ILLEGAL_COLLECTION_EXCEPTION_MESSAGE);
        }

        int compResult = (new NumberCollection(o1)).compareTo(new NumberCollection(o2));
        switch (compResult) {
            case 1: return GREATER_AVERAGE_VALUE_MESSAGE;
            case -1: return LESS_AVERAGE_VALUE_MESSAGE;
            default: return EQUAL_AVERAGE_VALUE_MESSAGE;
        }
    }
}
