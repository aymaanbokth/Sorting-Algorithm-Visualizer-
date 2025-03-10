FROM openjdk:17-jdk AS build
COPY . .
run gradle clean build

# Use an official Java 17 runtime as a parent image
FROM openjdk:17-jdk

# Set the working directory inside the container
WORKDIR /app

# Copy the built JAR file into the container
COPY build/libs/sorting-visualizer-0.0.1-SNAPSHOT.jar app.jar

# Expose the port the application runs on (Spring Boot default is 8080)
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
