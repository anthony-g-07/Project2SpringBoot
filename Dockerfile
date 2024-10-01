# Backend Dockerfile

# Use the official OpenJDK image from the Docker Hub
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the Spring Boot jar file from the host machine to the container
COPY build/libs/*.jar app.jar

# Expose the application port
EXPOSE 8080

# Set the entry point for running the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]