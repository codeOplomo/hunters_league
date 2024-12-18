# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim as build

# Set the working directory
WORKDIR /app

# Copy the local code to the container
COPY . .

# Build the Spring Boot application
RUN ./mvnw clean package -DskipTests

# Use a smaller image to run the app
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the packaged application from the build container
COPY --from=build /app/target/hunters_league-0.0.1-SNAPSHOT.jar app.jar

# Expose the port the app runs on
EXPOSE 8443

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
