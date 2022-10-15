package optimization.algorithms.multi.noderiv;

import linear.Vectors;
import linear.vector.Vector;
import optimization.algorithms.multi.MultivariateCostFunction;

import java.util.Arrays;

import static linear.linalg.LinearAlgebra.*;
import static linear.linalg.Mutability.IMMUTABLE;
import static linear.linalg.Mutability.MUTABLE;

/**
 * Abstract implementation of a simplex optimization method
 */
public abstract class AbstractSimplexMethod extends AbstractMultivariateOptimizer {

    protected AbstractSimplexMethod(MultivariateCostFunction function) {
        super(function);
    }

    protected AbstractSimplexMethod(MultivariateCostFunction function, double epsilon) {
        super(function, epsilon);
    }

    @Override
    public Vector search(Vector x0) {
        validate(x0);

        final Vector[] X = initialSimplex(x0);
        final double[] fX = Arrays.stream(X).mapToDouble(function::valueAt).toArray();

        return optimize(X, fX);
    }

    protected abstract void validate(Vector x0);

    protected abstract Vector[] initialSimplex(Vector x0);

    protected abstract Vector optimize(Vector[] X, double[] fX);

    protected Vector centroid(Vector[] simplex, int h) {
        int n = simplex.length;
        Vector centroid = Vectors.empty(simplex[0].getDimension());
        for (int i = 0; i < n; i++) {
            if (i == h) continue;
            add(centroid, simplex[i], MUTABLE);
        }
        return multiply(centroid, 1. / (n - 1), MUTABLE);
    }

    protected Vector reflection(Vector xc, Vector xh, double alpha) {
        return subtract(
                multiply(xc, 1 + alpha, IMMUTABLE),
                multiply(xh, alpha, IMMUTABLE),
                MUTABLE);
    }

    protected Vector expansion(Vector xc, Vector xr, double gamma) {
        return add(
                multiply(xc, 1 - gamma, IMMUTABLE),
                multiply(xr, gamma, IMMUTABLE),
                MUTABLE);
    }

    protected Vector contraction(Vector xc, Vector xh, double beta) {
        return add(
                multiply(xc, 1 - beta, IMMUTABLE),
                multiply(xh, beta, IMMUTABLE),
                MUTABLE);
    }

    protected Vector shrink(Vector xi, Vector xl, double sigma) {
        return multiply(
                add(xi, xl, MUTABLE),
                sigma,
                MUTABLE);
    }

    protected boolean testConvergence(double[] fX, double fxc) {
        double val = 0.;
        for (double fx : fX) {
            val += Math.pow(fx - fxc, 2);
        }
        return Math.sqrt(val / (fX.length - 1)) <= epsilon;
    }

    protected int argMin(double[] array) {
        int index = 0;
        double min = array[0];
        for (int i = 1, n = array.length; i < n; i++) {
            double temp = array[i];
            if (temp < min) {
                min = temp;
                index = i;
            }
        }
        return index;
    }
}
