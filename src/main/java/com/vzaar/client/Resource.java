package com.vzaar.client;

import com.fasterxml.jackson.databind.JavaType;
import com.vzaar.Lookup;
import com.vzaar.Page;
import com.vzaar.VzaarException;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

public class Resource<T> {
    private final RestClient client;
    private final Class<T> type;
    private String resource;
    private Integer id;
    private String action;
    private Object params;
    private Object payload;
    private byte[] body;

    public Resource(RestClient client, Class<T> type) {
        this.client = client;
        this.type = type;
    }

    public Resource<T> resource(String resource) {
        this.resource = resource;
        return this;
    }

    public Resource<T> id(Integer id) {
        this.id = id;
        return this;
    }

    public Resource<T> action(String action) {
        this.action = action;
        return this;
    }

    public Page<T> page(Object params) {
        this.params = params;
        client.get(this);
        try {
            return client.getObjectMapper().readValue(body, wrapper(Page.class));
        } catch (IOException e) {
            throw new VzaarException(e);
        }
    }

    public T lookup(int id) {
        this.id = id;
        client.get(this);
        try {
            Lookup<T> data = client.getObjectMapper().readValue(body, wrapper(Lookup.class));
            return data.getData();
        } catch (IOException e) {
            throw new VzaarException(e);
        }
    }

    public T create(Object request) {
        this.payload = request;
        client.post(this, request);
        try {
            Lookup<T> data = client.getObjectMapper().readValue(body, wrapper(Lookup.class));
            return data.getData();
        } catch (IOException e) {
            throw new VzaarException(e);
        }
    }

    public T update(Object request) {
        this.payload = request;
        client.patch(this, request);
        try {
            Lookup<T> data = client.getObjectMapper().readValue(body, wrapper(Lookup.class));
            return data.getData();
        } catch (IOException e) {
            throw new VzaarException(e);
        }
    }

    public void delete() {
        client.delete(this);
    }

    private JavaType wrapper(Class wrapperType) {
        return client.getObjectMapper().getTypeFactory().constructParametricType(wrapperType, type);
    }

    Resource<T> body(byte[] body) {
        this.body = body;
        return this;
    }

    URI getUri() throws URISyntaxException {
        StringBuilder uri = new StringBuilder(client.getEndpoint())
                .append("/")
                .append(Objects.requireNonNull(resource, "resource is required"));

        if (id != null) {
            uri.append("/").append(id);
        }

        if (action != null) {
            uri.append("/").append(action);
        }

        if (params != null) {
            uri.append(client.getParameterMapper().write(params));
        }

        return new URI(uri.toString());
    }
}
