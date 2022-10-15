package optimization.exceptions;

/**
 * Exception class used when constraints are not met
 */
public class ConstraintsNotMetException extends RuntimeException {

    public ConstraintsNotMetException(String message) {
        super(message);
    }
}
