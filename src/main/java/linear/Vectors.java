package linear;

import linear.linalg.LinearAlgebra;
import linear.linalg.Mutability;
import linear.vector.Vector;
import linear.vector.ListVector;
import linear.vector.ArrayVector;

import java.util.List;
import java.util.function.DoubleSupplier;

/**
 * Utility class with factory methods for building vectors and checking properties
 */
public class Vectors {

    /**
     * Builds a new empty vector
     *
     * @param dimension dimension of empty vector
     * @return new empty vector
     */
    public static Vector empty(int dimension) {
        return new ArrayVector(dimension);
    }

    /**
     * Builds a new vector, consisting of elements in given range
     *
     * @param start start of range
     * @param end   end of range, exclusive
     * @return new range vector
     */
    public static Vector range(int start, int end) {
        return new ArrayVector(start, end);
    }


    /**
     * Builds a new vector and fills it with ones
     *
     * @param dimension dimension of null vector
     * @return new vector
     */
    public static Vector ones(int dimension) {
        return fill(empty(dimension), 1.);
    }

    /**
     * Fills vector with given value
     *
     * @param vector vector to be filled
     * @param value  value
     * @return filled vector
     */
    public static Vector fill(Vector vector, double value) {
        return LinearAlgebra.apply(vector, x -> value, Mutability.MUTABLE);
    }

    /**
     * Fills vector using given supplier
     *
     * @param vector   vector to be filled
     * @param supplier supplier
     * @return filled vector
     */
    public static Vector fill(Vector vector, DoubleSupplier supplier) {
        return LinearAlgebra.apply(vector, x -> supplier.getAsDouble(), Mutability.MUTABLE);
    }

    /**
     * Creates a vector that serves as view of the given array
     *
     * @param array underlying array
     * @return new vector
     */
    public static Vector asVector(double... array) {
        return new ArrayVector(array);
    }

    /**
     * Creates a vector that serves as view of the given list
     *
     * @param list underlying list
     * @return new vector
     */
    public static Vector asVector(List<Double> list) {
        return new ListVector(list);
    }
}
