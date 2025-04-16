package com.back.bpo.labs.ticketing.platform.libs.exceptions;

import com.back.bpo.labs.ticketing.platform.libs.dto.ErrorResponseDTO;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class GlobalExceptionHandler implements ExceptionMapper<Throwable> {

    @Override
    public Response toResponse(Throwable exception) {
        ErrorResponseDTO errorResponse;

        if (exception instanceof EntidadInvalidaException) {
            errorResponse = new ErrorResponseDTO(400, "Entidad inválida", exception.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(errorResponse).build();
        } else if (exception instanceof ErrorBsonException) {
            errorResponse = new ErrorResponseDTO(422, "Error BSON", exception.getMessage());
            return Response.status(422).entity(errorResponse).build();
        } else if (exception instanceof ErrorMongoEscrituraException) {
            errorResponse = new ErrorResponseDTO(500, "Error de escritura en MongoDB", exception.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorResponse).build();
        } else if (exception instanceof ErrorMongoTimeoutException) {
            errorResponse = new ErrorResponseDTO(504, "Tiempo de espera excedido con MongoDB", exception.getMessage());
            return Response.status(Response.Status.GATEWAY_TIMEOUT).entity(errorResponse).build();
        } else if (exception instanceof ErrorPersistenciaGenericaException) {
            errorResponse = new ErrorResponseDTO(500, "Error de persistencia", exception.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorResponse).build();
        } else {
            // Error no controlado
            errorResponse = new ErrorResponseDTO(500, "Error inesperado", exception.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorResponse).build();
        }
    }
}
