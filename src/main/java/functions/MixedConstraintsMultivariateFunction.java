package functions;

import linear.vector.Vector;
import functions.constraints.EqualityConstraint;
import functions.constraints.InequalityConstraint;

import java.util.Objects;

/**
 * Implementation of the {@code IConstrainedMultivariateFunction} interface
 */
public class MixedConstraintsMultivariateFunction implements ConstrainedMultivariateFunction {

    protected final MultivariateFunction unconstrainedFunction;

    private final EqualityConstraint[] equalityConstraints;
    private final InequalityConstraint[] inequalityConstraints;

    private double coefficient;

    private static final double DEFAULT_COEFFICIENT = 1.;

    private static final EqualityConstraint[] EQUALITY_CONSTRAINTS_PLACEHOLDER = new EqualityConstraint[]{};
    private static final InequalityConstraint[] INEQUALITY_CONSTRAINTS_PLACEHOLDER = new InequalityConstraint[]{};

    public MixedConstraintsMultivariateFunction(MultivariateFunction unconstrainedFunction,
                                                EqualityConstraint[] equalityConstraints, InequalityConstraint[] inequalityConstraints,
                                                double coefficient) {
        this.unconstrainedFunction = Objects.requireNonNull(unconstrainedFunction);
        this.equalityConstraints = Objects.requireNonNull(equalityConstraints);
        this.inequalityConstraints = Objects.requireNonNull(inequalityConstraints);
        this.coefficient = coefficient;
    }

    public MixedConstraintsMultivariateFunction(MultivariateFunction unconstrainedFunction,
                                                EqualityConstraint[] equalityConstraints, InequalityConstraint[] inequalityConstraints) {
        this(unconstrainedFunction, equalityConstraints, inequalityConstraints, DEFAULT_COEFFICIENT);
    }

    public MixedConstraintsMultivariateFunction(MultivariateFunction unconstrainedFunction, InequalityConstraint... inequalityConstraints) {
        this(unconstrainedFunction, EQUALITY_CONSTRAINTS_PLACEHOLDER, inequalityConstraints);
    }

    public MixedConstraintsMultivariateFunction(MultivariateFunction unconstrainedFunction, EqualityConstraint... equalityConstraints) {
        this(unconstrainedFunction, equalityConstraints, INEQUALITY_CONSTRAINTS_PLACEHOLDER);
    }

    public MixedConstraintsMultivariateFunction(MultivariateFunction unconstrainedFunction, InequalityConstraint[] inequalityConstraints, double coefficient) {
        this(unconstrainedFunction, EQUALITY_CONSTRAINTS_PLACEHOLDER, inequalityConstraints, coefficient);
    }

    public MixedConstraintsMultivariateFunction(MultivariateFunction unconstrainedFunction, EqualityConstraint[] equalityConstraints, double coefficient) {
        this(unconstrainedFunction, equalityConstraints, INEQUALITY_CONSTRAINTS_PLACEHOLDER, coefficient);
    }

    @Override
    public double getCoefficient() {
        return coefficient;
    }

    @Override
    public void setCoefficient(double coefficient) {
        this.coefficient = coefficient;
    }

    public EqualityConstraint[] getEqualityConstraints() {
        return equalityConstraints;
    }

    public InequalityConstraint[] getInequalityConstraints() {
        return inequalityConstraints;
    }

    @Override
    public double valueAt(Vector x) {
        double value = unconstrainedFunction.valueAt(x);

        double inequalityCoefficient = 1. / coefficient;
        for (InequalityConstraint constraint : inequalityConstraints) {
            double constraintFunctionValue = constraint.getFunction().valueAt(x);

            if (constraintFunctionValue <= 0) return Double.POSITIVE_INFINITY;

            value -= inequalityCoefficient * Math.log(constraintFunctionValue);
        }
        for (EqualityConstraint constraint : equalityConstraints) {
            double constraintFunctionValue = constraint.getFunction().valueAt(x);
            value += coefficient * constraintFunctionValue * constraintFunctionValue;
        }

        return value;
    }
}
