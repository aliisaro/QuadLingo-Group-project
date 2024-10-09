# Stage 1: Build the application
FROM maven:3.8.5-openjdk-17 AS builder

# Copy your project files into the container
COPY --chown=maven:maven . /home/maven/QuadLingo
WORKDIR /home/maven/QuadLingo

# Build the application using Maven (skip tests temporarily)
RUN mvn clean package -DskipTests

# Stage 2: Create the runtime environment
FROM openjdk:17-jdk

# Copy the built JAR from the builder stage
COPY --from=builder /home/maven/QuadLingo/target/QuadLingo-1.0-SNAPSHOT.jar /QuadLingo.jar

# Install required packages for running JavaFX applications
RUN apt-get update && \
    apt-get install --no-install-recommends -y xorg libgl1-mesa-glx && \
    rm -rf /var/lib/apt/lists/*

# Specify the command to run your application
CMD ["java", "-jar", "/QuadLingo.jar"]
