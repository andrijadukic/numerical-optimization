package optimization.algorithms.uni;

import functions.UnivariateFunction;
import util.Interval;

/**
 * Implementation of the golden section search algorithm
 */
public final class GoldenSectionSearch extends AbstractUnivariateOptimizer {

    private final double K = 0.5 * (Math.sqrt(5) - 1);

    private double h = DEFAULT_H;

    private static final double DEFAULT_H = 1;

    public GoldenSectionSearch(UnivariateFunction function) {
        super(function);
    }

    public GoldenSectionSearch(UnivariateFunction function, double epsilon, double h) {
        super(function, epsilon);
        this.h = h;
    }

    public double getH() {
        return h;
    }

    public void setH(double h) {
        this.h = h;
    }

    @Override
    public double search(double x0) {
        Interval interval = findInterval(UnimodalInterval.find(function, h, x0));
        return (interval.start() + interval.end()) / 2.;
    }

    public Interval findInterval(Interval interval) {
        double a = interval.start();
        double b = interval.end();

        double c = b - K * (b - a);
        double d = a + K * (b - a);

        double fc = function.valueAt(c);
        double fd = function.valueAt(d);

        while ((b - a) > epsilon) {
            if (fc < fd) {
                b = d;
                d = c;
                c = b - K * (b - a);
                fd = fc;
                fc = function.valueAt(c);
            } else {
                a = c;
                c = d;
                d = a + K * (b - a);
                fc = fd;
                fd = function.valueAt(d);
            }
        }

        return new Interval(a, b);
    }

    @Override
    public String getName() {
        return "Golden section search";
    }
}
