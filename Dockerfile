# Use a lightweight Java Runtime Environment base image (Java 17 JRE on Alpine Linux)
FROM eclipse-temurin:17-jre-alpine

# Set the working directory inside the container
WORKDIR /app

# Argument to specify the path to your JAR file.
# This path is relative to the Dockerfile (i.e., your project root).
# Make sure this matches the actual path and name of your JAR file in the target directory.
ARG JAR_FILE_PATH=target/book-server-0.0.1-SNAPSHOT.jar

# Copy the pre-built JAR file from your project's target directory into the image and rename it to app.jar
COPY ${JAR_FILE_PATH} app.jar

# Expose the port that your Spring Boot application listens on internally (default is 8080)
EXPOSE 8080


# The command to run your application when the container starts
ENTRYPOINT ["java", "-jar", "/app.jar"]