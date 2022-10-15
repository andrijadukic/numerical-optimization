package functions;

import linear.matrix.Matrix;
import linear.vector.Vector;

/**
 * Represents a multivariate function which takes an n-dimensional vector as argument and returns a KxL dimensional matrix
 */
@FunctionalInterface
public interface MultivariableMatrixFunction {

    /**
     * Calculates matrix at given point
     *
     * @param x point
     * @return matrix
     */
    Matrix valueAt(Vector x);
}
