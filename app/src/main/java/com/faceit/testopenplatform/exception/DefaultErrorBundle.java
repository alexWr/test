package com.faceit.testopenplatform.exception;

/**
 * Wrapper around Exceptions used to manage default errors.
 */
public class DefaultErrorBundle implements ErrorBundle {

    private static final String DEFAULT_ERROR_MSG = "Unknown error";

    private Exception exception;
    private String errorMessage;

    public DefaultErrorBundle(Exception exception) {
        this.exception = exception;
    }

    public DefaultErrorBundle(String exception) {
        this.errorMessage = exception;
    }

    @Override
    public Exception getException() {
        return exception;
    }

    @Override
    public String getErrorMessage() {
        if (errorMessage != null)
            return errorMessage;
        else
            return (exception != null) ? this.exception.getMessage() : DEFAULT_ERROR_MSG;
    }
}
