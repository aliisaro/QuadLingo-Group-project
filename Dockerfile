# Stage 1: Build the application
FROM openjdk:17-slim AS build

# Install Maven and other necessary tools
RUN apt-get update && apt-get install -y maven wget unzip && rm -rf /var/lib/apt/lists/*

# Set the working directory
WORKDIR /app

# Copy the pom.xml and source code
COPY pom.xml .
COPY src ./src

# Package the application
RUN mvn clean package -DskipTests

# Stage 2: Create the final image with JavaFX
FROM openjdk:17-slim

# Install wget and unzip for downloading JavaFX
RUN apt-get update && apt-get install -y wget unzip && rm -rf /var/lib/apt/lists/*

# Set the working directory
WORKDIR /app

# Define JavaFX version
ENV JAVAFX_VERSION=17.0.2

# Download and install JavaFX SDK
RUN wget https://download2.gluonhq.com/openjfx/${JAVAFX_VERSION}/openjfx-${JAVAFX_VERSION}_linux-x64_bin-sdk.zip \
    && unzip openjfx-${JAVAFX_VERSION}_linux-x64_bin-sdk.zip \
    && mv javafx-sdk-${JAVAFX_VERSION} /opt/javafx \
    && rm openjfx-${JAVAFX_VERSION}_linux-x64_bin-sdk.zip

# Copy the JAR file from the build stage
COPY --from=build /app/target/QuadLingo.jar ./QuadLingo.jar

# Specify the command to run the JAR file with JavaFX modules
ENTRYPOINT ["java", "--module-path", "/opt/javafx/lib", "--add-modules", "javafx.controls,javafx.graphics", "-jar", "QuadLingo.jar"]
