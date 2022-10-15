package functions.constraints;

import functions.MultivariateFunction;

import java.util.Objects;

/**
 * Abstract class representing equality or inequality constraints
 */
public abstract class ImplicitConstraint implements Constraint {

    protected final MultivariateFunction function;

    public ImplicitConstraint(MultivariateFunction function) {
        this.function = Objects.requireNonNull(function);
    }

    public MultivariateFunction getFunction() {
        return function;
    }
}
