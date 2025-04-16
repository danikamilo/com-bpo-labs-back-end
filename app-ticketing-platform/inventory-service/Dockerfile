# Usar openjdk 17 como base
FROM openjdk:17-alpine

WORKDIR /work/
COPY target/inventory-service-1.0.0-runner.jar app.jar

EXPOSE 5054
CMD ["java", "-jar", "app.jar"]
