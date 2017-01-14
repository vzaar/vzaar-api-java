package com.vzaar.util;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class RequestParameterMapper {
    private static PropertyNamingStrategy.SnakeCaseStrategy strategy =
            new PropertyNamingStrategy.SnakeCaseStrategy();

    private final ObjectMapper objectMapper = ObjectMapperFactory.make();

    public <T> Map<String, String> writeToMap(T object) {
        try {
            Map<String, String> values = new LinkedHashMap<>();
            for (Field field : getDeclaredFields(object.getClass())) {
                field.setAccessible(true);
                if (!Modifier.isTransient(field.getModifiers())) {
                    Object value = field.get(object);
                    if (value != null) {
                        values.put(strategy.translate(field.getName()), String.valueOf(value));
                    }
                }
            }
            return values;
        } catch (IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
    }

    public <T> String write(T object) {
        try {
            Map<String, String> params = writeToMap(object);
            StringBuilder paramString = new StringBuilder();
            for (Entry<String, String> entry : params.entrySet()) {
                paramString
                        .append(paramString.length() == 0 ? '?' : '&')
                        .append(entry.getKey())
                        .append('=')
                        .append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            }
            return paramString.toString();
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException(e);
        }
    }

    public <T> T read(URL url, Class<T> type) {
        try {
            Map<String, String> params = splitQuery(url);
            return objectMapper.readValue(objectMapper.writeValueAsString(params), type);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private <T> List<Field> getDeclaredFields(Class<T> type) {
        List<Field> fields = new ArrayList<>();
        Class clazz = type;
        while (!clazz.equals(Object.class)) {
            fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass();
        }
        return fields;
    }

    private static Map<String, String> splitQuery(URL url) throws UnsupportedEncodingException {
        Map<String, String> queryPairs = new LinkedHashMap<>();
        String query = url.getQuery();
        String[] pairs = query.split("&");
        for (String pair : pairs) {
            int idx = pair.indexOf("=");
            queryPairs.put(URLDecoder.decode(pair.substring(0, idx), "UTF-8"), URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
        }
        return queryPairs;
    }
}
