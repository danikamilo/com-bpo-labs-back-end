package com.back.bpo.labs.ticketing.platform.libs.exceptions;

import com.back.bpo.labs.ticketing.platform.libs.dto.ErrorResponseDTO;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Global exception handler that maps custom exceptions to HTTP responses.
 * This class uses the ExceptionMapper interface to handle various types of exceptions globally.
 * It provides consistent error responses in JSON format.
 *
 * Author: Daniel Camilo
 */
@Provider
public class GlobalExceptionHandler implements ExceptionMapper<Throwable> {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @Override
    public Response toResponse(Throwable exception) {
        ErrorResponseDTO errorResponse;

        // Log the full error in the console
        logError(exception);

        if (exception instanceof InvalidEntityException) {
            errorResponse = new ErrorResponseDTO(400, "Invalid entity", exception.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(errorResponse).build();
        } else if (exception instanceof ErrorBsonException) {
            errorResponse = new ErrorResponseDTO(422, "BSON error", exception.getMessage());
            return Response.status(422).entity(errorResponse).build();
        } else if (exception instanceof MongoWriteErrorException) {
            errorResponse = new ErrorResponseDTO(500, "MongoDB write error", exception.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorResponse).build();
        } else if (exception instanceof MongoTimeoutErrorException) {
            errorResponse = new ErrorResponseDTO(504, "MongoDB timeout exceeded", exception.getMessage());
            return Response.status(Response.Status.GATEWAY_TIMEOUT).entity(errorResponse).build();
        } else if (exception instanceof GenericPersistenceErrorException) {
            errorResponse = new ErrorResponseDTO(500, "Persistence error", exception.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorResponse).build();
        } else {
            // Unhandled error
            errorResponse = new ErrorResponseDTO(500, "Unexpected error", exception.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorResponse).build();
        }
    }

    /**
     * Logs the exception to the console for better observability.
     * If it's an unexpected exception, the full stack trace is logged.
     *
     * @param exception The exception to be logged
     */
    private void logError(Throwable exception) {
        if (exception instanceof Exception) {
            LOGGER.error("Unexpected error occurred: ", exception);
        } else {
            LOGGER.error("Error occurred: " + exception.getMessage());
        }
    }
}