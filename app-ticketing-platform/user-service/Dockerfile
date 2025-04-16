# Usar openjdk 17 como base
FROM openjdk:17-alpine

WORKDIR /work/
COPY target/user-service-1.0.0-runner.jar app.jar

EXPOSE 5059
CMD ["java", "-jar", "app.jar"]