FROM quay.io/quarkus/quarkus-micro-image:2.0
WORKDIR /work/
COPY target/payment-service-1.0.0.jar app.jar
EXPOSE 5057
CMD ["java", "-jar", "app.jar"]
