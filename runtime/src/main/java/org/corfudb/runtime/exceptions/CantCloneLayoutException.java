package org.corfudb.runtime.exceptions;

/**
 *
 * An exception that is thrown when the layout clone operation fails.
 *
 * @author Maithem
 */
public class CantCloneLayoutException extends RuntimeException {

    public CantCloneLayoutException(Throwable throwable) {
        super(throwable);
    }
}
