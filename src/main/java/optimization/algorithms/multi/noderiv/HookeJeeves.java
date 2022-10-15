package optimization.algorithms.multi.noderiv;

import linear.vector.Vector;
import optimization.algorithms.multi.MultivariateCostFunction;

import static linear.linalg.LinearAlgebra.*;
import static linear.linalg.Mutability.*;

/**
 * Implementation of the Hooke-Jeeves algorithm
 */
public final class HookeJeeves extends AbstractMultivariateOptimizer {

    private double delta = DEFAULT_DELTA;

    private static final double DEFAULT_DELTA = 0.5;

    public HookeJeeves(MultivariateCostFunction function) {
        super(function);
    }

    public HookeJeeves(MultivariateCostFunction function, double epsilon, double delta) {
        super(function, epsilon);
        this.delta = delta;
    }

    public double getDelta() {
        return delta;
    }

    public void setDelta(double delta) {
        this.delta = delta;
    }

    @Override
    public Vector search(Vector x0) {
        Vector xp = x0;
        Vector xb = x0;

        double fxb = function.valueAt(xb);
        double dx = delta;
        while (dx >= epsilon) {
            Vector xn = explore(xp, dx);
            double fxn = function.valueAt(xn);
            if (fxn < fxb) {
                xp = subtract(multiply(xn, 2, IMMUTABLE), xb, MUTABLE);
                xb = xn;
                fxb = fxn;
            } else {
                dx /= 2;
                xp = xb;
            }
        }

        return xb;
    }

    private Vector explore(Vector xp, double dx) {
        Vector x = xp.copy();
        double fxInitial = function.valueAt(x);
        for (int i = 0, n = x.getDimension(); i < n; i++) {
            double xi = x.get(i);
            x.set(i, xi + dx);
            double fxNew = function.valueAt(x);
            if (fxNew > fxInitial) {
                x.set(i, xi - dx);
                fxNew = function.valueAt(x);
                if (fxNew > fxInitial) {
                    x.set(i, xi);
                } else {
                    fxInitial = fxNew;
                }
            } else {
                fxInitial = fxNew;
            }
        }
        return x;
    }

    @Override
    public String getName() {
        return "Hooke-Jeeves";
    }
}
