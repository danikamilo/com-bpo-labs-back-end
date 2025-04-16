# Dockerfile para event-service
FROM quay.io/quarkus/quarkus-micro-image:2.0

WORKDIR /work/
COPY target/event-service-1.0.0.jar application.jar

EXPOSE 5051

CMD ["java", "-jar", "application.jar"]



