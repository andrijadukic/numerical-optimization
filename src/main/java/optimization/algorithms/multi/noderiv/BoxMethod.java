package optimization.algorithms.multi.noderiv;

import linear.vector.ArrayVector;
import linear.vector.Vector;
import optimization.algorithms.multi.MultivariateCostFunction;
import optimization.exceptions.DivergenceLimitReachedException;
import optimization.exceptions.ExplicitConstraintsNotMetException;
import optimization.exceptions.ImplicitConstraintsNotMetException;
import functions.constraints.Constraints;
import functions.constraints.ExplicitConstraint;
import functions.constraints.ImplicitConstraint;
import util.IntPair;

import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static linear.linalg.LinearAlgebra.*;
import static linear.linalg.Mutability.*;

/**
 * Implementation of the box simplex method
 */
public class BoxMethod extends AbstractSimplexMethod {

    private final ExplicitConstraint[] explicitConstraints;
    private final ImplicitConstraint[] implicitConstraints;

    private double alpha = DEFAULT_ALPHA;
    private int divergenceLimit = DEFAULT_DIVERGENCE_LIMIT;

    private static final double DEFAULT_ALPHA = 1.3;
    private static final int DEFAULT_DIVERGENCE_LIMIT = 200;

    public BoxMethod(MultivariateCostFunction function, ExplicitConstraint[] explicitConstraints, ImplicitConstraint[] implicitConstraints) {
        super(function);
        this.explicitConstraints = Objects.requireNonNull(explicitConstraints);
        this.implicitConstraints = Objects.requireNonNull(implicitConstraints);
    }

    public BoxMethod(MultivariateCostFunction function,
                     ExplicitConstraint[] explicitConstraints, ImplicitConstraint[] implicitConstraints,
                     double epsilon, double alpha, int divergenceLimit) {
        super(function, epsilon);
        this.explicitConstraints = Objects.requireNonNull(explicitConstraints);
        this.implicitConstraints = Objects.requireNonNull(implicitConstraints);
        this.alpha = alpha;
        this.divergenceLimit = divergenceLimit;
    }

    public double getAlpha() {
        return alpha;
    }

    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }

    public int getDivergenceLimit() {
        return divergenceLimit;
    }

    public void setDivergenceLimit(int divergenceLimit) {
        this.divergenceLimit = divergenceLimit;
    }

    @Override
    protected void validate(Vector x0) {
        if (!Constraints.test(x0, explicitConstraints)) throw new ExplicitConstraintsNotMetException();
        if (!Constraints.test(x0, implicitConstraints)) throw new ImplicitConstraintsNotMetException();
    }

    @Override
    protected Vector[] initialSimplex(Vector x0) {
        int n = x0.getDimension();
        int size = n * 2;
        Vector[] simplex = new ArrayVector[size];
        simplex[0] = x0.copy();
        Vector centroid = x0.copy();
        for (int i = 1; i < size; i++) {
            Vector candidate = adjust(buildCandidate(x0, explicitConstraints), centroid, implicitConstraints);
            simplex[i] = candidate;
            centroid = add(multiply(subtract(candidate, centroid, IMMUTABLE), 1. / (i + 1), MUTABLE), centroid, MUTABLE);
        }

        return simplex;
    }

    protected Vector buildCandidate(Vector x0, ExplicitConstraint[] explicitConstraints) {
        Random random = ThreadLocalRandom.current();
        int n = x0.getDimension();
        Vector candidate = x0.newInstance(n);
        for (int i = 0; i < n; i++) {
            ExplicitConstraint constraint = explicitConstraints[i];
            double lb = constraint.lowerbound();
            double ub = constraint.upperbound();
            candidate.set(i, lb + random.nextDouble() * (ub - lb));
        }
        return candidate;
    }

    protected Vector adjust(Vector point, Vector centroid, ImplicitConstraint[] implicitConstraints) {
        int count = 0;
        while (!Constraints.test(point, implicitConstraints)) {
            if (count > divergenceLimit) throw new DivergenceLimitReachedException(divergenceLimit);
            point = shift(point, centroid);
            count++;
        }
        return point;
    }

    protected Vector adjust(Vector point, ExplicitConstraint[] explicitConstraints) {
        for (int i = 0, n = point.getDimension(); i < n; i++) {
            ExplicitConstraint constraint = explicitConstraints[i];
            double lb = constraint.lowerbound();
            double ub = constraint.upperbound();
            if (point.get(i) < lb) {
                point.set(i, lb);
            } else if (point.get(i) > ub) {
                point.set(i, ub);
            }
        }
        return point;
    }

    protected Vector shift(Vector point, Vector centroid) {
        return multiply(add(point, centroid, MUTABLE), 0.5, MUTABLE);
    }

    @Override
    protected Vector optimize(Vector[] X, double[] fX) {
        Vector min = X[argMin(fX)];
        double best = function.valueAt(min);
        int count = 0;
        while (true) {
            if (count > divergenceLimit)
                throw new DivergenceLimitReachedException(divergenceLimit, "minimum found: [" + min + "]");

            IntPair worst = worstTwo(fX);
            int h = worst.first();
            int h2 = worst.second();

            Vector xc = centroid(X, h);

            if (testConvergence(fX, function.valueAt(xc))) break;

            Vector xr = reflection(xc, X[h], alpha);

            xr = adjust(xr, explicitConstraints);
            xr = adjust(xr, xc, implicitConstraints);
            if (function.valueAt(xr) > function.valueAt(X[h2])) {
                xr = shift(xr, xc);
            }

            X[h] = xr;
            fX[h] = function.valueAt(xr);

            min = X[argMin(fX)];
            double value = function.valueAt(min);
            if (value < best) {
                best = value;
                count = 0;
            } else {
                count++;
            }
        }
        return min;
    }

    private IntPair worstTwo(double[] array) {
        int maxIndex = 0;
        int secondMaxIndex = 0;
        double maxValue = array[0];
        double secondMaxValue = array[0];
        for (int i = 1, n = array.length; i < n; i++) {
            double temp = array[i];
            if (temp > maxValue) {
                maxValue = temp;
                maxIndex = i;
            } else if (temp > secondMaxValue) {
                secondMaxValue = temp;
                secondMaxIndex = i;
            }
        }
        return new IntPair(maxIndex, secondMaxIndex);
    }

    @Override
    public String getName() {
        return "Box method";
    }
}
