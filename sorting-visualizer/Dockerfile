# Use OpenJDK 17 and set up a multi-stage build
FROM openjdk:17-jdk AS build

# Set working directory inside the container
WORKDIR /app

# Copy Gradle files first (to leverage caching)
COPY gradle gradle
COPY gradlew .
COPY build.gradle .
COPY settings.gradle .

# Ensure Gradle wrapper is executable
RUN chmod +x gradlew

# Copy source code after Gradle setup (to improve caching)
COPY src src

# Build the project
RUN ./gradlew clean build --no-daemon

# ---- Production Image ----
FROM openjdk:17-jdk

# Set the working directory inside the container
WORKDIR /app

# Copy the built JAR file from the previous stage
COPY --from=build /app/build/libs/*.jar app.jar

# Expose the port the application runs on
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
