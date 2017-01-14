package com.vzaar;

import java.util.List;

public class VzaarServerException extends VzaarException {
    private final int statusCode;
    private final String statusMessage;
    private final List<VzaarError> errors;

    public VzaarServerException(int statusCode, String statusMessage, VzaarErrorList errors) {
        super(statusMessage);
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
        this.errors = errors.getErrors();
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public List<VzaarError> getErrors() {
        return errors;
    }
}
