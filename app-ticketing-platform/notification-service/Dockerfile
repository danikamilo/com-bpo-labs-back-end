# Usar openjdk 17 como base
FROM openjdk:17-alpine

# Instalar telnet
RUN apk update && apk add --no-cache busybox-extras

WORKDIR /work/
COPY target/notification-service-1.0.0-runner.jar app.jar

EXPOSE 5055
CMD ["java", "-jar", "app.jar"]
