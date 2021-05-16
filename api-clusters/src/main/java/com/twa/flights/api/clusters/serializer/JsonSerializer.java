package com.twa.flights.api.clusters.serializer;

import java.io.IOException;

import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
import static com.fasterxml.jackson.databind.MapperFeature.USE_GETTERS_AS_SETTERS;
import static com.fasterxml.jackson.databind.PropertyNamingStrategy.SNAKE_CASE;

@NoArgsConstructor
public class JsonSerializer {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonSerializer.class);
    private static final ObjectMapper MAPPER;

    static {
        MAPPER = new ObjectMapper().configure(USE_GETTERS_AS_SETTERS, false)
                .configure(FAIL_ON_UNKNOWN_PROPERTIES, false).setPropertyNamingStrategy(SNAKE_CASE)
                .registerModule(new JavaTimeModule());
    }

    public static byte[] serialize(Object object) {
        byte[] compressedJson = null;
        try {
            compressedJson = MAPPER.writeValueAsString(object).getBytes();
        } catch (IOException e) {
            LOGGER.error("Error serializing object: {}", e.getMessage());
        }
        return compressedJson;
    }

    public static <T> T deserialize(byte[] raw, Class<T> reference) {
        if (raw == null)
            return null;

        T object = null;
        try {
            object = MAPPER.readValue(raw, reference);
        } catch (IOException e) {
            LOGGER.error("Can't deserialize object: {}", e.getMessage());
        }
        return object;
    }

}
