package optimization.algorithms.multi;

import functions.ConstrainedMultivariateFunction;
import functions.constraints.EqualityConstraint;
import functions.constraints.InequalityConstraint;

/**
 * Implementation of the {@code IMultivariableCostFunction} interface used for constrained optimization problems
 */
public class ConstrainedMultivariateCostFunction extends MultivariateCostFunction {

    protected final ConstrainedMultivariateFunction constrainedFunction;

    public ConstrainedMultivariateCostFunction(ConstrainedMultivariateFunction constrainedFunction) {
        super(constrainedFunction);
        this.constrainedFunction = constrainedFunction;
    }

    public double getCoefficient() {
        return constrainedFunction.getCoefficient();
    }

    public void setCoefficient(double coefficient) {
        constrainedFunction.setCoefficient(coefficient);
    }

    public InequalityConstraint[] getInequalityConstraints() {
        return constrainedFunction.getInequalityConstraints();
    }

    public EqualityConstraint[] getEqualityConstraints() {
        return constrainedFunction.getEqualityConstraints();
    }
}
