package com.meteoradesigner.util.exception;

import lombok.AllArgsConstructor;

/**
 * This @code{ErrorInfo} class represents the error information.
 */
@AllArgsConstructor
public class ErrorInfo {
    private final String requestUrl;
    private final String description;
    private final String primeCause;
}
