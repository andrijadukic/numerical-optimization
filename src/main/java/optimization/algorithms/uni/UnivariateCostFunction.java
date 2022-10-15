package optimization.algorithms.uni;

import functions.UnivariateFunction;

import java.util.Objects;

/**
 * Implementation of the {@code ISingleVariableCostFunction} interface
 */
public class UnivariateCostFunction implements UnivariateFunction {

    protected final UnivariateFunction function;
    protected int functionEvalCounter;

    public UnivariateCostFunction(UnivariateFunction function) {
        this.function = Objects.requireNonNull(function);
    }

    public int getFunctionEvaluationCount() {
        return functionEvalCounter;
    }

    public void reset() {
        functionEvalCounter = 0;
    }

    @Override
    public double valueAt(double x) {
        functionEvalCounter++;
        return function.valueAt(x);
    }
}