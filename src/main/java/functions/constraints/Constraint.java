package functions.constraints;

import linear.vector.Vector;

import java.util.Objects;

/**
 * Represents a constraint on a function
 */
@FunctionalInterface
public interface Constraint {

    /**
     * Tests if given point fits this constraint
     *
     * @param x point
     * @return true if point fits constraint, false otherwise
     */
    boolean test(Vector x);

    /**
     * Combines two constraints with logical and operator
     *
     * @param next constraint to be added to this constraint
     * @return new constraint
     */
    default Constraint and(Constraint next) {
        Objects.requireNonNull(next);
        return x -> test(x) && next.test(x);
    }

    /**
     * Combines two constraints with logical or operator
     *
     * @param next constraint to be added to this constraint
     * @return new constraint
     */
    default Constraint or(Constraint next) {
        Objects.requireNonNull(next);
        return x -> test(x) || next.test(x);
    }

    /**
     * Creates an inverse constraint (returns true if orginal constraint returned false and vice versa)
     *
     * @return new constraint
     */
    default Constraint not() {
        return x -> !test(x);
    }
}
