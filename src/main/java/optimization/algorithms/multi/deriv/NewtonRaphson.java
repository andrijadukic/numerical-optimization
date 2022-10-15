package optimization.algorithms.multi.deriv;

import linear.decompose.LUPDecomposer;
import linear.vector.Vector;

import static linear.linalg.LinearAlgebra.multiply;
import static linear.linalg.Mutability.IMMUTABLE;

/**
 * Implementation of the Newton-Raphson algorithm
 */
public final class NewtonRaphson extends AbstractDifferentiableMultivariateOptimizer {

    public NewtonRaphson(DifferentiableMultivariateCostFunction function) {
        super(function);
    }

    public NewtonRaphson(DifferentiableMultivariateCostFunction function, double epsilon, int maxIter, boolean computeOptimalStep) {
        super(function, epsilon, maxIter, computeOptimalStep);
    }

    @Override
    protected Vector computeDirection(Vector x, Vector gradient) {
        return new LUPDecomposer(function.hessian(x)).solver().solve(multiply(gradient, -1, IMMUTABLE));
    }

    @Override
    public String getName() {
        return "Newton Raphson";
    }
}
