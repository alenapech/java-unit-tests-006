package org.alenapech;

import java.util.ArrayList;
import java.util.Collection;

public class NumberCollection extends ArrayList<Number> implements Comparable<NumberCollection> {
    public NumberCollection(Collection<? extends Number> c) {
        super(c);
    }

    @Override
    public int compareTo(NumberCollection o) {
        return this.getAverage().compareTo(o.getAverage());
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof NumberCollection)) {
            return false;
        }

        NumberCollection that = (NumberCollection) o;
       return this.getAverage() == that.getAverage();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    private Double getAverage() {
        if (size() == 0)
            return 0d;
        double sum = 0;
        for (Number number : this) {
            sum += number.doubleValue();
        }
        return sum / this.size();
    }
}
