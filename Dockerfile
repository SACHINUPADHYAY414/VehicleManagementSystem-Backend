# ----------- Step 1: Build the application using Maven ----------- #
FROM maven:3.9.6-eclipse-temurin-21 AS builder

WORKDIR /app

# Cache dependencies
COPY pom.xml mvnw ./
COPY .mvn .mvn
RUN mvn dependency:go-offline -B

# Copy source code
COPY src ./src

# Build project
RUN mvn clean package -DskipTests

# ----------- Step 2: Create a lightweight production image ----------- #
FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY --from=builder /app/target/vehiclehub-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENV JAVA_OPTS="-XX:+UseContainerSupport \
               -XX:MaxRAMPercentage=75.0 \
               -XX:InitialRAMPercentage=50.0 \
               -XX:+HeapDumpOnOutOfMemoryError \
               -XX:HeapDumpPath=/tmp \
               -XX:+UseG1GC"

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
