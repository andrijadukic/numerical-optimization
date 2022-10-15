package optimization.algorithms.uni;

import optimization.algorithms.util.NamedAlgorithm;

/**
 * Represents a single variable function minimization algorithm
 */
public interface UnivariateOptimizer extends NamedAlgorithm {

    /**
     * Computes the minimum of a function from a starting point
     *
     * @param x0 starting point
     * @return argmin
     */
    double search(double x0);
}
