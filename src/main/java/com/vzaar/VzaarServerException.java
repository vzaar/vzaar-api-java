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

    /**
     * Get the HTTP status code
     * @return the status code
     */
    public int getStatusCode() {
        return statusCode;
    }

    /**
     * Get the HTTP status message
     * @return the status message
     */
    public String getStatusMessage() {
        return statusMessage;
    }

    /**
     * Get the errors returned by the vzaar server
     * @return the list of errors
     */
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
