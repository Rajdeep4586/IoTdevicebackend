# Use OpenJDK 17 base image
FROM openjdk:17-jdk-slim

# Set work directory inside container
WORKDIR /app

# Copy the exact JAR file
COPY target/IoTdevicebackend-0.0.1-SNAPSHOT.jar app.jar

# Expose port
EXPOSE 9090

# Run Spring Boot app
CMD ["java", "-jar", "app.jar"]

