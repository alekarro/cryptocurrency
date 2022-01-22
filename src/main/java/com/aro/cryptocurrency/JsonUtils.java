package com.aro.cryptocurrency;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonUtils {
    private static final Logger LOG = LoggerFactory.getLogger(JsonUtils.class);

    public static String toJson(final Object obj) {
        try {
            return new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_EMPTY).writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            LOG.error("Error: object " + obj +" cannot be converted to string", e);
        }
        return "";
    }

    public static Object toObject(final String json, final Class aClass) {
        try {
            return new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_EMPTY).readValue(json, aClass);
        } catch (JsonProcessingException e) {
            LOG.error("Error: json " + json +" cannot be converted to Object", e);
        }
        return null;
    }

}
