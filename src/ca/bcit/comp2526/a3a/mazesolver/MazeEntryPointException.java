package ca.bcit.comp2526.a3a.mazesolver;

/**
 * MazeEntryPointException is thrown when a maze file is opened that does not
 * have a correctly-located entry point.
 *
 * @author BCIT
 * @version 2016
 */
@SuppressWarnings("serial")
public class MazeEntryPointException extends Exception {

    /**
     * Constructor for objects of type MazeEntryPointException.
     */
    public MazeEntryPointException() {
    }

    /**
     * Constructor for objects of type MazeEntryPointException.
     * 
     * @param message
     */
    public MazeEntryPointException(String message) {
        super(message);
    }

    /**
     * Constructor for objects of type MazeEntryPointException.
     * 
     * @param cause
     */
    public MazeEntryPointException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructor for objects of type MazeEntryPointException.
     * 
     * @param message
     * @param cause
     */
    public MazeEntryPointException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor for objects of type MazeEntryPointException.
     * 
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public MazeEntryPointException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
