package com.vzaar;

import java.util.List;

public class VzaarServerException extends VzaarException {
    private final int statusCode;
    private final String statusMessage;
    private final List<VzaarError> errors;

    public VzaarServerException(int statusCode, String statusMessage, VzaarErrorList errors) {
        super(buildMessage(statusCode, statusMessage, errors));
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

    private static String buildMessage(int statusCode, String statusMessage, VzaarErrorList errors) {
        StringBuilder sb = new StringBuilder();
        sb.append(statusCode).append(": ").append(statusMessage);

        for (VzaarError error : errors.getErrors()) {
            sb.append("\n  - ").append(error.getMessage())
                    .append("\n    ").append(error.getDetail());
        }
        return sb.toString();
    }
}
