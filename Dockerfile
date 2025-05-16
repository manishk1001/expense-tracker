# ---- Stage 1: Build ----
FROM gradle:8.5-jdk21 AS builder
WORKDIR /app

# Copy only necessary files to cache dependencies
COPY gradlew ./
COPY gradle gradle
COPY build.gradle settings.gradle ./

# Pre-download dependencies (improves cache efficiency)
RUN ./gradlew dependencies --no-daemon

# Copy the rest of the source
COPY . .

# Build the application (excluding tests)
RUN ./gradlew clean build -x test

# ---- Stage 2: Runtime ----
FROM openjdk:21-jdk-slim
WORKDIR /app

# Copy built JAR from builder stage
COPY --from=builder /app/build/libs/expense-tracker-0.0.1-SNAPSHOT.jar ./expense-tracker.jar

# Set environment variable via build arg
ARG ENV
ENV ENVIRONMENT=${ENV}

# Expose application port
EXPOSE 8080

# Run the application with the active profile
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=${ENVIRONMENT}", "expense-tracker.jar"]