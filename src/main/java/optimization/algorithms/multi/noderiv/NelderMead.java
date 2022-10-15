package optimization.algorithms.multi.noderiv;

import linear.vector.ArrayVector;
import linear.vector.Vector;
import optimization.algorithms.multi.MultivariateCostFunction;
import util.IntPair;

/**
 * Implementation of the Nelder-Mead simplex method
 */
public class NelderMead extends AbstractSimplexMethod {

    private double alpha = DEFAULT_ALPHA;
    private double beta = DEFAULT_BETA;
    private double gamma = DEFAULT_GAMMA;
    private double sigma = DEFAULT_SIGMA;
    private double step = DEFAULT_STEP;

    private static final double DEFAULT_ALPHA = 1.0;
    private static final double DEFAULT_BETA = 0.5;
    private static final double DEFAULT_GAMMA = 2.0;
    private static final double DEFAULT_SIGMA = 0.5;
    private static final double DEFAULT_STEP = 1.0;

    public NelderMead(MultivariateCostFunction function) {
        super(function);
    }

    public NelderMead(MultivariateCostFunction function, double epsilon, double alpha, double beta, double gamma, double sigma, double step) {
        super(function, epsilon);
        this.alpha = alpha;
        this.beta = beta;
        this.gamma = gamma;
        this.sigma = sigma;
        this.step = step;
    }

    public double getAlpha() {
        return alpha;
    }

    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }

    public double getBeta() {
        return beta;
    }

    public void setBeta(double beta) {
        this.beta = beta;
    }

    public double getGamma() {
        return gamma;
    }

    public void setGamma(double gamma) {
        this.gamma = gamma;
    }

    public double getSigma() {
        return sigma;
    }

    public void setSigma(double sigma) {
        this.sigma = sigma;
    }

    public double getStep() {
        return step;
    }

    public void setStep(double step) {
        this.step = step;
    }

    @Override
    protected void validate(Vector x0) {
    }

    @Override
    protected Vector[] initialSimplex(Vector x0) {
        int n = x0.getDimension() + 1;
        Vector[] simplex = new ArrayVector[n];
        simplex[0] = x0.copy();
        for (int i = 1; i < n; i++) {
            int nthDimension = i - 1;
            simplex[i] = x0.copy().set(nthDimension, x0.get(nthDimension) + step);
        }
        return simplex;
    }

    @Override
    protected Vector optimize(Vector[] X, double[] fX) {
        while (true) {
            IntPair worstAndBest = worstAndBest(fX);
            int h = worstAndBest.first();
            int l = worstAndBest.second();

            Vector xc = centroid(X, h);

            if (testConvergence(fX, function.valueAt(xc))) break;

            Vector xr = reflection(xc, X[h], alpha);

            double fxr = function.valueAt(xr);
            if (fxr < fX[l]) {
                Vector xe = expansion(xc, xr, gamma);
                double fxe = function.valueAt(xe);
                if (fxe < fX[l]) {
                    X[h] = xe;
                    fX[h] = fxe;
                } else {
                    X[h] = xr;
                    fX[h] = fxr;
                }
            } else {
                boolean isConditionMet = true;
                for (int i = 0, n = fX.length; i < n; i++) {
                    if (i == h) continue;
                    if (fxr < fX[i]) {
                        isConditionMet = false;
                        break;
                    }
                }
                if (isConditionMet) {
                    if (fxr < fX[h]) {
                        X[h] = xr;
                        fX[h] = fxr;
                    }
                    Vector xk = contraction(xc, X[h], beta);
                    double fxk = function.valueAt(xk);
                    if (fxk < fX[h]) {
                        X[h] = xk;
                        fX[h] = fxk;
                    } else {
                        Vector xl = X[l];
                        for (int i = 0, n = X.length; i < n; i++) {
                            if (i == l) continue;
                            X[i] = shrink(X[i], xl, sigma);
                            fX[i] = function.valueAt(X[i]);
                        }
                    }
                } else {
                    X[h] = xr;
                    fX[h] = fxr;
                }
            }
        }

        return X[argMin(fX)];
    }

    private IntPair worstAndBest(double[] array) {
        double maxValue, minValue;
        int maxIndex, minIndex;

        maxValue = minValue = array[0];
        maxIndex = minIndex = 0;
        for (int i = 1, n = array.length; i < n; i++) {
            double temp = array[i];
            if (temp > maxValue) {
                maxValue = temp;
                maxIndex = i;
            } else if (temp < minValue) {
                minValue = temp;
                minIndex = i;
            }
        }
        return new IntPair(maxIndex, minIndex);
    }

    @Override
    public String getName() {
        return "Nelder Mead";
    }
}
