package optimization.exceptions;

/**
 * Exception class used when explicit constraints are not met
 */
public class ExplicitConstraintsNotMetException extends ConstraintsNotMetException {

    public ExplicitConstraintsNotMetException() {
        super("Explicit constraints were not met");
    }
}
