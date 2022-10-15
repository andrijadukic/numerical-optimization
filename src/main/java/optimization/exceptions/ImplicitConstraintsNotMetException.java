package optimization.exceptions;

/**
 * Exception class used when implicit constraints are not met
 */
public class ImplicitConstraintsNotMetException extends ConstraintsNotMetException {

    public ImplicitConstraintsNotMetException() {
        super("Implicit constraints were not met");
    }
}
