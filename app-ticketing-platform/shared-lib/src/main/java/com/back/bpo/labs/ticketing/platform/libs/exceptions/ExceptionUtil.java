package com.back.bpo.labs.ticketing.platform.libs.exceptions;


import com.mongodb.MongoWriteException;
import com.mongodb.MongoTimeoutException;
import org.bson.BsonInvalidOperationException;


/**
 * @author Daniel Camilo
 */
public class ExceptionUtil {

    private ExceptionUtil() {}

    public static RuntimeException handlePersistenceException(Throwable e) {
        if (e instanceof IllegalArgumentException) {
            return new EntidadInvalidaException("Entidad inválida: " + e.getMessage(), e);
        } else if (e instanceof BsonInvalidOperationException) {
            return new ErrorBsonException("Error BSON en la entidad: " + e.getMessage(), e);
        } else if (e instanceof MongoWriteException) {
            return new ErrorMongoEscrituraException("Error al escribir en MongoDB: " + e.getMessage(), e);
        } else if (e instanceof MongoTimeoutException) {
            return new ErrorMongoTimeoutException("MongoDB no está disponible o tiempo de espera agotado.", e);
        } else {
            return new ErrorPersistenciaGenericaException("Error inesperado al persistir: " + e.getMessage(), e);
        }
    }
}
