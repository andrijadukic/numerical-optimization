package optimization.algorithms.uni;

import functions.UnivariateFunction;
import util.Interval;

import java.util.Objects;

/**
 * Static implementation of the unimodal interval search algorithm
 */
public final class UnimodalInterval {

    public static Interval find(UnivariateFunction function, double h, double x0) {
        Objects.requireNonNull(function);

        double l = x0 - h;
        double m = x0;
        double r = x0 + h;
        double fl, fm, fr;
        int step = 1;

        fl = function.valueAt(l);
        fm = function.valueAt(m);
        fr = function.valueAt(r);

        if (fm < fl && fm < fr) return new Interval(l, r);

        if (fm > fr) {
            do {
                l = m;
                m = r;
                fm = fr;
                r = x0 + h * (step *= 2);
                fr = function.valueAt(r);
            } while (fm > fr);
        } else {
            do {
                r = m;
                m = l;
                fm = fl;
                l = x0 - h * (step *= 2);
                fl = function.valueAt(l);
            } while (fm > fl);
        }

        return new Interval(l, r);
    }
}
