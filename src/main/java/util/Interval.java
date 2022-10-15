package util;

import java.security.InvalidParameterException;

/**
 * Record class for storing intervals
 */
public record Interval(double start, double end) {

    public Interval {
        if (start > end) throw new InvalidParameterException();
    }

    @Override
    public String toString() {
        return "[" + start + ", " + end + "]";
    }
}
