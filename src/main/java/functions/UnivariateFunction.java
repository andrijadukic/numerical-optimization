package functions;

/**
 * Represents a single variable function
 */
@FunctionalInterface
public interface UnivariateFunction {

    /**
     * Calculates value at given point
     *
     * @param x point
     * @return real number in double precision
     */
    double valueAt(double x);

    /**
     * Creates new function which returns negative value of the original (f2(x) = -f1(x))
     *
     * @return new function
     */
    default UnivariateFunction negate() {
        return x -> -valueAt(x);
    }
}
