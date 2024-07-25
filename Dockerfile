FROM openjdk:17-jdk
WORKDIR /app
COPY target/product-service-0.0.1-SNAPSHOT.jar /app/product-service.jar
CMD ["java", "-jar", "/app/product-service.jar"]