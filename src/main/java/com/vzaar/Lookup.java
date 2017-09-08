package com.vzaar;

/**
 * Wrapper class for single entities
 * @param <T> the entity type
 */
public class Lookup<T> {
    private T data;

    public T getData() {
        return data;
    }
}
