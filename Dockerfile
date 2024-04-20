FROM ubuntu:latest
LABEL authors="bilel"

ENTRYPOINT ["top", "-b"]
# Use a base image with Java 21 Corretto installed
FROM amazoncorretto:21-alpine

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file into the container
COPY target/api-finologee-bank-1.0.0.jar /app/api-finologee-bank-1.0.0.jar


CMD ["./mvnw", "spring-boot:run"]
# Expose the port that your Spring Boot application listens on
EXPOSE 8080

# Specify the command to run your Spring Boot application
CMD ["java", "-jar", "api-finologee-bank-1.0.0.jar"]
