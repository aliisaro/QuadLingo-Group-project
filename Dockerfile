# Use OpenJDK base image
FROM openjdk:17-slim AS build

# Install Maven
RUN apt-get update && apt-get install -y maven && rm -rf /var/lib/apt/lists/*

# Set the working directory
WORKDIR /app

# Copy the pom.xml and the source code into the container
COPY pom.xml .
COPY src ./src

# Package the application
RUN mvn clean package -DskipTests

# Set the working directory in the final image
FROM openjdk:17-slim

WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=build /app/target/QuadLingo.jar ./QuadLingo.jar

# Specify the command to run the JAR file
ENTRYPOINT ["java", "--module-path", "/path/to/javafx/lib", "--add-modules", "javafx.controls,javafx.fxml", "-jar", "QuadLingo.jar"]
