package com.back.bpo.labs.ticketing.platform.libs.exceptions;

import com.mongodb.MongoWriteException;
import com.mongodb.MongoTimeoutException;
import org.bson.BsonInvalidOperationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class to handle persistence-related exceptions and translate them into custom exceptions.
 * This promotes consistent error handling and maps MongoDB-specific exceptions into domain-specific ones.
 *
 * Author: Daniel Camilo
 */
public class ExceptionUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionUtil.class);

    // Private constructor to prevent instantiation
    private ExceptionUtil() {}

    /**
     * Handles different types of persistence exceptions and maps them to custom runtime exceptions.
     * Also logs the exception to aid in monitoring and debugging.
     *
     * @param e The exception to be handled
     * @return The mapped application-specific RuntimeException
     */
    public static RuntimeException handlePersistenceException(Throwable e) {
        logError(e);

        if (e instanceof IllegalArgumentException) {
            return new InvalidEntityException("Invalid entity: " + e.getMessage(), e);
        } else if (e instanceof BsonInvalidOperationException) {
            return new ErrorBsonException("BSON error in entity: " + e.getMessage(), e);
        } else if (e instanceof MongoWriteException) {
            return new MongoWriteErrorException("Error writing to MongoDB: " + e.getMessage(), e);
        } else if (e instanceof MongoTimeoutException) {
            return new MongoTimeoutErrorException("MongoDB is unavailable or timeout exceeded.", e);
        } else if (e instanceof NoContentException) {
            return new NoContentException("The database query did not return any data.");
        } else if (e instanceof TicketsNotAvailableException) {
            return new TicketsNotAvailableException("Not enough tickets available.");
        } else {
            return new GenericPersistenceErrorException("Unexpected error during persistence: " + e.getMessage(), e);
        }
    }

    /**
     * Logs the exception to the console for visibility and observability.
     *
     * @param e The exception to be logged
     */
    private static void logError(Throwable e) {
        if (e instanceof Exception) {
            LOGGER.error("Unexpected error occurred: ", e);
        } else {
            LOGGER.error("Error occurred: " + e.getMessage());
        }
    }
}