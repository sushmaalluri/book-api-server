# Stage 1: Build the application using Maven and OpenJDK 17
FROM maven:3.9.6-eclipse-temurin-17-alpine AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn package -DskipTests

# Stage 2: Create the final lightweight runtime image
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/target/book-server-0.0.1-SNAPSHOT.jar app.jar
RUN ls -l /app
EXPOSE 8080 
ENTRYPOINT ["sh", "-c", "echo '--- Runtime: Listing /app contents ---' && ls -l /app && echo '--- Runtime: Attempting to run java -jar /app.jar ---' && java -jar /app.jar"]
