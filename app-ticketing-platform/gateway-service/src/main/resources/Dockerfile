FROM quay.io/quarkus/quarkus-micro-image:2.0
WORKDIR /work/
COPY target/gateway-service-1.0.0.jar app.jar
EXPOSE 5053
CMD ["java", "-jar", "app.jar"]
