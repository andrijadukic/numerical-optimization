package optimization.algorithms.multi.deriv;

import linear.vector.Vector;

import static linear.linalg.LinearAlgebra.*;
import static linear.linalg.Mutability.*;

/**
 * Implementation of the gradient descent algorithm
 */
public final class GradientDescent extends AbstractDifferentiableMultivariateOptimizer {

    public GradientDescent(DifferentiableMultivariateCostFunction function) {
        super(function);
    }

    public GradientDescent(DifferentiableMultivariateCostFunction function, double epsilon, int maxIter, boolean computeOptimalStep) {
        super(function, epsilon, maxIter, computeOptimalStep);
    }

    @Override
    protected Vector computeDirection(Vector x, Vector gradient) {
        return multiply(gradient, -1., IMMUTABLE);
    }

    @Override
    public String getName() {
        return "Gradient descent";
    }
}
