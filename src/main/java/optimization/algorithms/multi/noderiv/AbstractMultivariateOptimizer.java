package optimization.algorithms.multi.noderiv;

import optimization.algorithms.multi.MultivariateCostFunction;

import java.util.Objects;

/**
 * Abstract helper class for storing common properties of {@code IMultivariableOptimizationAlgorithm} classes
 */
abstract class AbstractMultivariateOptimizer implements MultivariateOptimizer {

    protected final MultivariateCostFunction function;

    protected double epsilon = DEFAULT_EPSILON;

    protected static double DEFAULT_EPSILON = 1e-6;

    protected AbstractMultivariateOptimizer(MultivariateCostFunction function) {
        this.function = Objects.requireNonNull(function);
    }

    protected AbstractMultivariateOptimizer(MultivariateCostFunction function, double epsilon) {
        this(function);
        this.epsilon = epsilon;
    }

    public double getEpsilon() {
        return epsilon;
    }

    public void setEpsilon(double epsilon) {
        this.epsilon = epsilon;
    }
}
