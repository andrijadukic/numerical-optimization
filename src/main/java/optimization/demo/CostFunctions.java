package optimization.demo;

import linear.matrix.ArrayMatrix;
import linear.vector.Vector;
import optimization.algorithms.multi.deriv.DifferentiableMultivariateCostFunction;

/**
 * Utility class defining several cost functions through static factory methods
 */
public class CostFunctions {

    /**
     * f(x) = 100 * (x2 - x1^2)^2 + (1 - x1)^2
     *
     * @return cost function f1
     */
    public static DifferentiableMultivariateCostFunction f1() {
        return new DifferentiableMultivariateCostFunction(
                x -> {
                    double x1 = x.get(0);
                    double x2 = x.get(1);
                    return 100 * Math.pow(x2 - Math.pow(x1, 2), 2) + Math.pow(1 - x1, 2);
                },
                x -> {
                    double x1 = x.get(0);
                    double x2 = x.get(1);
                    return Vector.of(-400 * x1 * x2 + 400 * Math.pow(x1, 3) - 2 + 2 * x1, 200 * x2 - 200 * Math.pow(x1, 2));
                },
                x -> {
                    double x1 = x.get(0);
                    double x2 = x.get(1);
                    return new ArrayMatrix(new double[][]{
                            {-400 * x2 + 1200 * Math.pow(x1, 2) + 2, -400 * x1},
                            {-400 * x1, 200}
                    });
                }
        );
    }

    /**
     * f(x) = (x1 - 4)^2 + 4 * (x2 - 2)^2
     *
     * @return cost function f2
     */
    public static DifferentiableMultivariateCostFunction f2() {
        return new DifferentiableMultivariateCostFunction(
                x -> Math.pow(x.get(0) - 4, 2) + 4 * Math.pow(x.get(1) - 2, 2),
                x -> Vector.of(2 * (x.get(0) - 4), 8 * (x.get(1) - 2)),
                x -> new ArrayMatrix(new double[][]{
                        {2, 0},
                        {0, 8},
                })
        );
    }

    /**
     * f(x) = (x1 - 2)^2 + 4 * (x2 + 3)^2
     *
     * @return cost function f3
     */
    public static DifferentiableMultivariateCostFunction f3() {
        return new DifferentiableMultivariateCostFunction(
                x -> Math.pow(x.get(0) - 2, 2) + 4 * Math.pow(x.get(1) + 3, 2),
                x -> Vector.of(2 * (x.get(0) - 2), 2 * (x.get(1) + 3)),
                x -> new ArrayMatrix(new double[][]{
                        {2, 0},
                        {0, 2},
                })
        );
    }

    /**
     * f(x) = (x1 - 3)^2 + (x2)^2
     *
     * @return cost function f4
     */
    public static DifferentiableMultivariateCostFunction f4() {
        return new DifferentiableMultivariateCostFunction(
                x -> Math.pow(x.get(0) - 3, 2) + Math.pow(x.get(1), 2),
                x -> Vector.of(2 * (x.get(0) - 3), 2 * x.get(1)),
                x -> new ArrayMatrix(new double[][]{
                        {2, 0},
                        {0, 2}})
        );
    }
}
