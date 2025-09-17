# Stage 1: Build
FROM maven:3.9.6-eclipse-temurin-21 AS builder

WORKDIR /app

# Copy source code
COPY . .

# Compile and package while allowing access to internal APIs
RUN mvn clean package -DskipTests \
    -Dmaven.compiler.release=21 \
    -Dmaven.compiler.showDeprecation=true \
    -Dmaven.compiler.forceJavacCompilerUse=true \
    -Dmaven.compiler.compilerArgs="--add-opens=jdk.compiler/com.sun.tools.javac.tree=ALL-UNNAMED"

# Stage 2: Run
FROM eclipse-temurin:21-jdk-jammy

WORKDIR /app

# Copy built jar from previous stage
COPY --from=builder /app/target/vehiclehub-0.0.1-SNAPSHOT.jar app.jar

# Run the Spring Boot app
ENTRYPOINT ["java", "-jar", "app.jar"]
