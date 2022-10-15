package optimization.exceptions;

/**
 * Exception class used when iteration count of a method exceeds the given limit
 */
public class DivergenceLimitReachedException extends RuntimeException {

    public DivergenceLimitReachedException(int divergenceLimit) {
        super("Divergence limit reached (" + divergenceLimit + ")");
    }

    public DivergenceLimitReachedException(int divergenceLimit, String message) {
        super("Divergence limit reached (" + divergenceLimit + "), " + message);
    }
}
