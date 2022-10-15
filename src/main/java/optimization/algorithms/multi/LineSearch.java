package optimization.algorithms.multi;

import linear.vector.Vector;
import optimization.algorithms.uni.GoldenSectionSearch;
import optimization.algorithms.uni.UnivariateOptimizer;
import functions.MultivariateFunction;

import java.util.Objects;

import static linear.linalg.LinearAlgebra.multiply;
import static linear.linalg.LinearAlgebra.add;
import static linear.linalg.Mutability.IMMUTABLE;
import static linear.linalg.Mutability.MUTABLE;

/**
 * Implementation of the line search algorithm
 */
public final class LineSearch implements UnivariateOptimizer {

    private final MultivariateFunction function;
    private final Vector x;
    private final Vector direction;

    public LineSearch(MultivariateFunction function, Vector x, Vector direction) {
        this.function = Objects.requireNonNull(function);
        this.x = Objects.requireNonNull(x);
        this.direction = Objects.requireNonNull(direction);
    }

    @Override
    public double search(double l0) {
        return new GoldenSectionSearch(
                lambda -> function.valueAt(add(multiply(direction, lambda, IMMUTABLE), x, MUTABLE)))
                .search(l0);
    }

    @Override
    public String getName() {
        return "Line search";
    }
}
