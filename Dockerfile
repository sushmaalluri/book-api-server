# Stage 1: Build the application using Maven and OpenJDK 17
FROM maven:3.9.6-eclipse-temurin-17-alpine AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn package -DskipTests

# Stage 2: Create the final runtime image using a standard JRE (not Alpine)
FROM eclipse-temurin:17-jre 
WORKDIR /app
COPY --from=build /app/target/book-server-0.0.1-SNAPSHOT.jar app.jar

# Keep your debugging ENTRYPOINT for now, or revert to the simpler one if this works
ENTRYPOINT ["sh", "-c", "echo '--- Runtime: Listing /app contents ---' && ls -l /app && echo '--- Runtime: Attempting to run java -jar /app.jar ---' && java -jar /app.jar"]
# Original ENTRYPOINT was: ENTRYPOINT ["java", "-jar", "/app.jar"]

EXPOSE 8080
