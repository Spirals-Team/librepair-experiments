package edu.hm.hafner.analysis;

/**
 * Indicates that parsing has been canceled due to a user initiated interrupt.
 *
 * @author Ullrich Hafner
 */
public class ParsingCanceledException extends RuntimeException {
    private static final long serialVersionUID = 3341274949787014225L;

    /**
     * Creates a new instance of {@link ParsingCanceledException}.
     */
    public ParsingCanceledException() {
        super("Canceling parsing since build has been aborted.");
    }
}

