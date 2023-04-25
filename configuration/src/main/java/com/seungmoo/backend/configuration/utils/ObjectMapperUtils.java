package com.seungmoo.backend.configuration.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ObjectMapperUtils {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public String toString(Object value) throws JsonProcessingException {
        return objectMapper.writeValueAsString(value);
    }

    public <T> T readString(String value, TypeReference<T> type) throws JsonProcessingException {
        return objectMapper.readValue(value, type);
    }

    public static <T> T convertValue(Object sessionUser, Class<T> tClass) {
        return objectMapper.convertValue(sessionUser, tClass);
    }
}
