package optimization.algorithms.uni;

import functions.UnivariateFunction;

import java.util.Objects;

/**
 * Abstract helper class for storing common properties of {@code ISingleVariableOptimizationAlgorithm} classes
 */
abstract class AbstractUnivariateOptimizer implements UnivariateOptimizer {

    protected final UnivariateFunction function;

    protected double epsilon = DEFAULT_EPSILON;

    protected static double DEFAULT_EPSILON = 1e-6;

    protected AbstractUnivariateOptimizer(UnivariateFunction function) {
        this.function = Objects.requireNonNull(function);
    }

    protected AbstractUnivariateOptimizer(UnivariateFunction function, double epsilon) {
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
