package functions;

import linear.Vectors;
import linear.vector.Vector;

import java.util.List;

/**
 * Represents a multivariable function
 */
@FunctionalInterface
public interface MultivariateFunction {

    /**
     * Calculates value at given point
     *
     * @param x point
     * @return real number in double precision
     */
    double valueAt(Vector x);

    /**
     * Calculates value at given point by wrapping given array into vector and calling the standard method
     *
     * @param x point
     * @return real number in double precision
     */
    default double valueAt(double... x) {
        return valueAt(Vectors.asVector(x));
    }

    /**
     * Calculates value at given point by wrapping given list into vector and calling the standard method
     *
     * @param x point
     * @return real number in double precision
     */
    default double valueAt(List<Double> x) {
        return valueAt(Vectors.asVector(x));
    }

    /**
     * Creates new function which returns negative value of the original (f2(x) = -f1(x))
     *
     * @return new function
     */
    default MultivariateFunction negate() {
        return x -> -valueAt(x);
    }
}
