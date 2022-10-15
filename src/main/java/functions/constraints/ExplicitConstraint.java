package functions.constraints;

import util.Interval;

public record ExplicitConstraint(double lowerbound, double upperbound) {

    public ExplicitConstraint(Interval interval) {
        this(interval.start(), interval.end());
    }

    public boolean test(double x) {
        return x >= lowerbound && x <= upperbound;
    }

    public static ExplicitConstraint any() {
        return new ExplicitConstraint(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
    }

    public static ExplicitConstraint nonNegative() {
        return new ExplicitConstraint(0., Double.POSITIVE_INFINITY);
    }

    public static ExplicitConstraint nonPositive() {
        return new ExplicitConstraint(Double.NEGATIVE_INFINITY, 0.);
    }
}
