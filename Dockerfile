# ----------- Step 1: Build Stage ----------- #
FROM maven:3.9.5-eclipse-temurin-21 AS build

# Set working directory
WORKDIR /app

# Copy Maven wrapper + config files first to leverage Docker caching
COPY pom.xml ./
COPY .mvn .mvn
COPY mvnw ./

RUN chmod +x mvnw

# Download Maven dependencies (will be cached unless pom.xml changes)
RUN ./mvnw dependency:go-offline

# Copy the full source code
COPY src ./src

# Build the application
RUN ./mvnw clean package -DskipTests

# ----------- Step 2: Run Stage ----------- #
FROM eclipse-temurin:21-jdk

# Set working directory
WORKDIR /app

# Copy jar from build stage
COPY --from=build /app/target/*.jar app.jar

# Expose default Spring Boot port
EXPOSE 8080

# Run the jar
ENTRYPOINT ["java", "-jar", "app.jar"]
