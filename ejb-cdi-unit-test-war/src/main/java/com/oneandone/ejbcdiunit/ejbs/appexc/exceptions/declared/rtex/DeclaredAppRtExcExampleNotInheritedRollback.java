package com.oneandone.ejbcdiunit.ejbs.appexc.exceptions.declared.rtex;

/**
 * @author aschoerk
 */
public class DeclaredAppRtExcExampleNotInheritedRollback extends RuntimeException {

    private static final long serialVersionUID = -4969387912800017883L;

    public DeclaredAppRtExcExampleNotInheritedRollback(String message) {
        super(message);
    }
}
