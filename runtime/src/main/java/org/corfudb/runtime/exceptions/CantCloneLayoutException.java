package org.corfudb.runtime.exceptions;

/**
 * @author Maithem
 */
public class CantCloneLayoutException extends RuntimeException {

    public CantCloneLayoutException(Throwable throwable) {
        super(throwable);
    }
}
