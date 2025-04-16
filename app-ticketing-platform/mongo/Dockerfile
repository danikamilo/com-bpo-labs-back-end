# Dockerfile para MongoDB
FROM mongo:6.0.13-jammy

# Definir las variables de entorno para inicializar MongoDB
# Esto se usa para crear un usuario root y una base de datos por defecto al inicializar el contenedor
ENV MONGO_INITDB_ROOT_USERNAME=bpo
ENV MONGO_INITDB_ROOT_PASSWORD=labs.123
ENV MONGO_INITDB_DATABASE=db_ticketmaster

# Exponer el puerto estándar de MongoDB (27017)
EXPOSE 27017

# Usar un volumen para almacenar los datos de la base de datos
# Esto permite persistencia de los datos incluso si el contenedor es eliminado
VOLUME ["/data/db"]

# Copiar cualquier archivo adicional si es necesario
# Por ejemplo, puedes incluir scripts de inicialización en el contenedor si es necesario
# COPY ./init-mongo.js /docker-entrypoint-initdb.d/

# Finalmente, el contenedor de MongoDB ya está listo para ser ejecutado