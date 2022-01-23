package com.aro.cryptocurrency.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonUtils {
    private static final ObjectMapper OBJECT_MAPPER;

    static {
        OBJECT_MAPPER = new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
    }

    public static String toJson(final Object obj) throws JsonProcessingException {
        try {
            return OBJECT_MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error("Error: object " + obj + " cannot be converted to string", e);
            throw e;
        }
    }

    public static <T> T toObject(final String json, final Class<T> aClass) throws JsonProcessingException {
        try {
            return OBJECT_MAPPER.readValue(json, aClass);
        } catch (JsonProcessingException e) {
            log.error("Error: json " + json + " cannot be converted to Object", e);
            throw e;
        }
    }

}
