# ---- Build Stage ----
# Use Debian-based image instead of Alpine
FROM openjdk:17-jdk-slim AS build  # Use Debian-based image instead of Alpine

# Set working directory inside the container
WORKDIR /app

# Install dependencies (Fix xargs issue)
RUN apt-get update && apt-get install -y xargs

# Copy Gradle wrapper and configuration first
COPY gradle gradle
COPY gradlew .
COPY build.gradle .
COPY settings.gradle .

# Ensure Gradle wrapper is executable
RUN chmod +x gradlew

# Copy the source code after caching dependencies
COPY src src

# Build the project
RUN ./gradlew clean build --no-daemon

# ---- Production Stage ----
FROM openjdk:17-jdk-slim  # Use the same Debian-based image for consistency

# Set the working directory inside the container
WORKDIR /app

# Copy the built JAR file from the previous stage
COPY --from=build /app/build/libs/*.jar app.jar

# Expose the port the application runs on
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
