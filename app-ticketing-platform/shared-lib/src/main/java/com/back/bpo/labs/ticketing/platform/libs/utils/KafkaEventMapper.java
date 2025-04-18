package com.back.bpo.labs.ticketing.platform.libs.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Utility class for mapping Kafka events to JSON.
 * This class serializes objects to JSON format for Kafka event publishing.
 *
 * Author: Daniel Camilo
 */
public class KafkaEventMapper {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaEventMapper.class);

    private static final ObjectMapper objectMapper = new ObjectMapper(); // Jackson ObjectMapper para deserializar JSON

    /**
     * Converts an object to its JSON representation.
     * If an error occurs during conversion, it logs the error.
     *
     * @param obj The object to be converted to JSON
     * @return The JSON string representation of the object
     * @throws JsonProcessingException If the conversion fails
     */
    public static String toJson(Object obj) throws JsonProcessingException {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            LOGGER.error("Error converting object to JSON: ", e);
            throw e; // Re-throw the exception to be handled by the caller
        }
    }

    /**
     * Converts a JSON string to an object of the specified class.
     *
     * @param json The JSON string to be converted
     * @param clazz The class of the object to be returned
     * @param <T> The type of the object
     * @return The deserialized object
     * @throws IOException If the conversion fails
     */
    public static <T> T toObject(String json, Class<T> clazz) throws IOException {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (IOException e) {
            LOGGER.error("Error converting JSON to object: ", e);
            throw e; // Re-throw the exception to be handled by the caller
        }
    }
}