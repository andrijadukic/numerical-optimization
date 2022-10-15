package optimization.algorithms.multi.deriv;

import functions.MultivariableMatrixFunction;
import functions.MultivariableVectorFunction;
import functions.MultivariateFunction;
import linear.matrix.Matrix;
import linear.vector.Vector;
import optimization.algorithms.multi.MultivariateCostFunction;

import java.util.Objects;

/**
 * Implementation of {@code IDifferentiableMultivariableCostFunction} interface
 */
public class DifferentiableMultivariateCostFunction extends MultivariateCostFunction {

    protected final MultivariableVectorFunction gradient;
    protected final MultivariableMatrixFunction hessian;

    protected int gradientEvalCounter;
    protected int hessianEvalCounter;

    public DifferentiableMultivariateCostFunction(MultivariateFunction function, MultivariableVectorFunction gradient, MultivariableMatrixFunction hessian) {
        super(function);
        this.gradient = Objects.requireNonNull(gradient);
        this.hessian = Objects.requireNonNull(hessian);
    }

    public int getGradientEvaluationCount() {
        return gradientEvalCounter;
    }

    public int getHessianEvaluationCount() {
        return hessianEvalCounter;
    }

    @Override
    public void reset() {
        super.reset();
        gradientEvalCounter = hessianEvalCounter = 0;
    }

    public Vector gradient(Vector x) {
        gradientEvalCounter++;
        return gradient.valueAt(x);
    }

    public Matrix hessian(Vector x) {
        hessianEvalCounter++;
        return hessian.valueAt(x);
    }
}
