package optimization.algorithms.multi.noderiv;

import functions.constraints.Constraints;
import functions.constraints.InequalityConstraint;
import linear.vector.Vector;
import optimization.algorithms.multi.ConstrainedMultivariateCostFunction;
import optimization.algorithms.multi.MultivariateCostFunction;

/**
 * Implementation of a constrained optimizer using Hooke-Jeeves
 */
public final class HookeJeevesConstrainedOptimizer extends AbstractConstrainedOptimizer {

    private double delta = DEFAULT_DELTA;

    private static final double DEFAULT_DELTA = 0.5;

    public HookeJeevesConstrainedOptimizer(ConstrainedMultivariateCostFunction function) {
        super(function);
    }

    public HookeJeevesConstrainedOptimizer(ConstrainedMultivariateCostFunction function,
                                           double coefficient, double epsilon, int divergenceLimit, double delta) {
        super(function, coefficient, epsilon, divergenceLimit);
        this.delta = delta;
    }

    public double getDelta() {
        return delta;
    }

    public void setDelta(double delta) {
        this.delta = delta;
    }

    @Override
    protected Vector interiorPoint(Vector x0, InequalityConstraint[] inequalityConstraints) {
        return new HookeJeeves(new MultivariateCostFunction(Constraints.sum(inequalityConstraints)), epsilon, delta).search(x0);
    }

    @Override
    protected Vector argMin(MultivariateCostFunction function, Vector x0) {
        return new HookeJeeves(function, epsilon, delta).search(x0);
    }

    @Override
    public String getName() {
        return "Hooke-Jeeves constrained optimizer";
    }
}
