package org.lwstudio.springtodo.exception;

import org.springframework.security.access.AccessDeniedException;

public class UnauthorizedException extends AccessDeniedException {
    private static final long serialVersionUID = 8127186461208138875L;

    public UnauthorizedException(String message) { super(message);  }
    public UnauthorizedException(String message, Throwable cause) { super(message, cause); }
}
