package com.vzaar.events;

import java.util.EventObject;

public class VzaarEvent extends EventObject {

    /**
     *
     */
    private static final long serialVersionUID = -3672900049384135987L;
    private Object data;

    public VzaarEvent(Object source) {
        super(source);
    }

    public VzaarEvent(Object source, Object data) {
        super(source);
        this.data = data;
    }

    public Object getData() {
        return data;
    }

}
