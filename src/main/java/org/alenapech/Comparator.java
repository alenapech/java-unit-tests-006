package org.alenapech;

import java.util.Collection;

public interface Comparator<T, V> {

    V compare(Collection<T> o1, Collection<T> o2);

}
