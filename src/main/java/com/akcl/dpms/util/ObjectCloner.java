package com.akcl.dpms.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectCloner<T> {
    private final T originalObject;

    public ObjectCloner(T cloneSource) {
        this.originalObject = cloneSource;
    }

    public T cloneObject(Class<T> tClass) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(objectMapper.writeValueAsString(originalObject), tClass);
    }
}
