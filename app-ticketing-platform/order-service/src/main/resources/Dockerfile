FROM quay.io/quarkus/quarkus-micro-image:2.0
WORKDIR /work/
COPY target/order-service-1.0.0.jar app.jar
EXPOSE 5056
CMD ["java", "-jar", "app.jar"]
