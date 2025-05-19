# Stage 1: Build the application (remains the same)
FROM maven:3.9.6-eclipse-temurin-17-alpine AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn package -DskipTests

# Stage 2: Create the final runtime image (using standard JRE)
FROM eclipse-temurin:17-jre 
WORKDIR /app
COPY --from=build /app/target/book-server-0.0.1-SNAPSHOT.jar app.jar

# More verbose debugging ENTRYPOINT
ENTRYPOINT ["sh", "-c", \
            "echo '--- [DEBUG] Container Runtime Start ---'; \
             echo '--- [DEBUG] Running as user:'; whoami; \
             echo '--- [DEBUG] Current directory (pwd):'; pwd; \
             echo '--- [DEBUG] Listing /app directory contents:'; ls -lA /app; \
             echo '--- [DEBUG] Java version being used:'; java -version; \
             echo '--- [DEBUG] Path to java executable:'; which java; \
             echo '--- [DEBUG] Attempting to execute: java -jar /app/app.jar'; \
             java -jar /app/app.jar; \
             echo '--- [DEBUG] Java execution finished, exit code was $? ---'"]

EXPOSE 8080