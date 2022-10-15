package optimization.algorithms.multi.noderiv;

import linear.vector.Vector;
import optimization.algorithms.multi.MultivariateCostFunction;
import optimization.algorithms.uni.GoldenSectionSearch;

/**
 * Implementation of the coordinate descent algorithm
 */
public class CoordinateDescent extends AbstractMultivariateOptimizer {

    public CoordinateDescent(MultivariateCostFunction function) {
        super(function);
    }

    public CoordinateDescent(MultivariateCostFunction function, double epsilon) {
        super(function, epsilon);
    }

    @Override
    public Vector search(Vector x0) {
        Vector x = x0.copy();
        int dimension = x0.getDimension();
        while (true) {
            Vector snapshot = x.copy();
            for (int i = 0; i < dimension; i++) {
                final int nthDimension = i;
                final double xi = x.get(nthDimension);
                x.set(nthDimension, xi + new GoldenSectionSearch(lambda -> function.valueAt(x.set(nthDimension, xi + lambda))).search(xi));
            }
            if (testConvergence(snapshot, x)) break;
        }
        return x;
    }

    protected boolean testConvergence(Vector previous, Vector current) {
        for (int i = 0, n = previous.getDimension(); i < n; i++) {
            if (Math.abs(previous.get(i) - current.get(i)) > epsilon) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String getName() {
        return "Coordinate descent";
    }
}
