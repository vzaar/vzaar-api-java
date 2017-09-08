package com.vzaar;

import java.util.ArrayList;
import java.util.List;

public class VzaarErrorList {
    private List<VzaarError> errors = new ArrayList<>();

    /**
     * Get the errors returned by the server
     * @return the errors returned by the server
     */
    public List<VzaarError> getErrors() {
        return errors;
    }
}
