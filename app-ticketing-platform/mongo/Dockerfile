# Usar la imagen oficial de MongoDB
FROM mongo:latest

# Definir variables de entorno para inicializar MongoDB con usuario y base de datos
ENV MONGO_INITDB_ROOT_USERNAME=bpo
ENV MONGO_INITDB_ROOT_PASSWORD=labs.123
ENV MONGO_INITDB_DATABASE=db_ticketmaster

# Exponer el puerto estándar de MongoDB
EXPOSE 27017

# Usar un volumen para almacenar los datos de la base de datos
VOLUME /data/db