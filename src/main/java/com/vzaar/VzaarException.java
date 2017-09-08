package com.vzaar;

public class VzaarException extends RuntimeException {

    public VzaarException(String message) {
        super(message);
    }

    public VzaarException(Throwable e) {
        super(e);
    }
}
