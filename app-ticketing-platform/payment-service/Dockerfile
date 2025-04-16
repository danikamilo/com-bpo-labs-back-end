# Usar openjdk 17 como base
FROM openjdk:17-alpine

WORKDIR /work/
COPY target/payment-service-1.0.0-runner.jar app.jar

EXPOSE 5057
CMD ["java", "-jar", "app.jar"]
