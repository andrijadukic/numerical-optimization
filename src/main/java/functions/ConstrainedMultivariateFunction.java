package functions;

import functions.constraints.EqualityConstraint;
import functions.constraints.InequalityConstraint;

/**
 * Represents a constrained multivariate function
 */
public interface ConstrainedMultivariateFunction extends MultivariateFunction {

    /**
     * Gets coefficient used for scaling constraint penalties
     *
     * @return coefficient
     */
    double getCoefficient();

    /**
     * Sets coefficient used for scaling constraint penalties
     *
     * @param coefficient coefficient
     */
    void setCoefficient(double coefficient);

    /**
     * Gets inequality constraints on this function
     *
     * @return inequality constraints
     */
    InequalityConstraint[] getInequalityConstraints();

    /**
     * Gets equality constraints on this function
     *
     * @return equality constraints
     */
    EqualityConstraint[] getEqualityConstraints();
}
