package optimization.demo;

import functions.MixedConstraintsMultivariateFunction;
import linear.vector.Vector;
import optimization.algorithms.multi.ConstrainedMultivariateCostFunction;
import optimization.algorithms.multi.MultivariateCostFunction;
import optimization.algorithms.multi.deriv.DifferentiableMultivariateCostFunction;
import optimization.algorithms.multi.deriv.GradientDescent;
import optimization.algorithms.multi.deriv.NewtonRaphson;
import optimization.algorithms.multi.noderiv.*;
import optimization.exceptions.DivergenceLimitReachedException;
import functions.constraints.*;

public class Demo {

    public static void main(String[] args) {
        example1();
        System.out.println();

        example2();
        System.out.println();

        example3();
        System.out.println();

        example4();
        System.out.println();

        example5();
    }

    private static void example1() {
        System.out.println("Example 1");
        System.out.println("---------");

        var f3 = CostFunctions.f3();
        Vector startingPoint = Vector.of(0., 0.);
        var gradientDescent = new GradientDescent(f3);

        System.out.println("Without line search:");
        gradientDescent.setComputeOptimalStep(false);
        test(f3, startingPoint, gradientDescent);

        System.out.println();

        System.out.println("With line search");
        gradientDescent.setComputeOptimalStep(true);
        test(f3, startingPoint, gradientDescent);
    }

    private static void example2() {
        System.out.println("Example 2");
        System.out.println("---------");

        System.out.println("Function f1:");
        System.out.println();
        var f1 = CostFunctions.f1();
        Vector startingPoint1 = Vector.of(-1.9, 2.);
        test(f1, startingPoint1, new GradientDescent(f1));
        System.out.println();
        test(f1, startingPoint1, new NewtonRaphson(f1));

        System.out.println();

        System.out.println("Function f2:");
        System.out.println();
        var f2 = CostFunctions.f2();
        Vector startingPoint2 = Vector.of(0.1, 0.3);
        test(f2, startingPoint2, new GradientDescent(f2));
        System.out.println();
        test(f2, startingPoint2, new NewtonRaphson(f2));
    }

    private static void example3() {
        System.out.println("Example 3");
        System.out.println("---------");

        var implicitConstraints = new ImplicitConstraint[]{
                Constraints.inequality(x -> x.get(1) - x.get(0)),
                Constraints.inequality(x -> 2 - x.get(0))};

        var explicitConstraints = new ExplicitConstraint[]{
                Constraints.explicit(-100., 100.),
                Constraints.explicit(-100, 100.)};

        System.out.println("Function f1:");
        System.out.println();
        var f1 = CostFunctions.f1();
        Vector startingPoint1 = Vector.of(-1.9, 2.);
        test(f1, startingPoint1, new BoxMethod(f1, explicitConstraints, implicitConstraints));

        System.out.println();

        System.out.println("Function f2:");
        System.out.println();
        var f2 = CostFunctions.f2();
        Vector startingPoint2 = Vector.of(0.1, 0.3);
        test(f2, startingPoint2, new BoxMethod(f2, explicitConstraints, implicitConstraints));
    }

    private static void example4() {
        System.out.println("Example 4");
        System.out.println("---------");

        var inequalityConstraints = new InequalityConstraint[]{
                Constraints.inequality(x -> x.get(1) - x.get(0)),
                Constraints.inequality(x -> 2 - x.get(0))};

        System.out.println("Function f1:");
        System.out.println();
        var constrained1 = new ConstrainedMultivariateCostFunction(new MixedConstraintsMultivariateFunction(CostFunctions.f1(), inequalityConstraints));
        Vector startingPoint1 = Vector.of(-1.9, 2.);
        var constrainedOptimizer = new HookeJeevesConstrainedOptimizer(constrained1);
        test(constrained1, startingPoint1, constrainedOptimizer);
        Vector newStartingPoint1 = Vector.of(1, 1.5);
        test(constrained1, newStartingPoint1, constrainedOptimizer);

        System.out.println();

        System.out.println("Function f2:");
        System.out.println();
        var constrained2 = new ConstrainedMultivariateCostFunction(new MixedConstraintsMultivariateFunction(CostFunctions.f2(), inequalityConstraints));
        Vector startingPoint2 = Vector.of(0.1, 0.3);
        test(constrained2, startingPoint2, new HookeJeevesConstrainedOptimizer(constrained2));
    }

    private static void example5() {
        System.out.println("Example 5");
        System.out.println("---------");

        var inequalityConstraints = new InequalityConstraint[]{
                Constraints.inequality(x -> 3 - x.get(0) - x.get(1)),
                Constraints.inequality(x -> 3 + 1.5 * x.get(0) - x.get(1))};
        var equalityConstraints = new EqualityConstraint[]{Constraints.equality(x -> x.get(1) - 1)};

        var function = new ConstrainedMultivariateCostFunction(new MixedConstraintsMultivariateFunction(CostFunctions.f4(), equalityConstraints, inequalityConstraints));
        Vector startingPoint = Vector.of(5., 5.);
        test(function, startingPoint, new HookeJeevesConstrainedOptimizer(function));
    }

    private static void test(DifferentiableMultivariateCostFunction function, Vector startingPoint, MultivariateOptimizer algorithm) {
        try {
            System.out.println("Algorithm: " + algorithm.getName());
            System.out.println("Min: [" + algorithm.search(startingPoint) + "]");
            System.out.println("Function evaluations: " + function.getFunctionEvaluationCount());
            System.out.println("Gradient evaluations: " + function.getGradientEvaluationCount());
            System.out.println("Hessian evaluations: " + function.getHessianEvaluationCount());
        } catch (DivergenceLimitReachedException e) {
            System.out.println(e.getMessage());
        } finally {
            function.reset();
        }
    }

    private static void test(MultivariateCostFunction function, Vector startingPoint, MultivariateOptimizer algorithm) {
        try {
            System.out.println("Algorithm: " + algorithm.getName());
            System.out.println("Min: [" + algorithm.search(startingPoint) + "]");
            System.out.println("Function evaluations: " + function.getFunctionEvaluationCount());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            function.reset();
        }
    }
}
