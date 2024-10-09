# Use Maven to build the project
FROM maven:3.8.5-openjdk-17 AS builder
WORKDIR /home/maven/QuadLingo
COPY . .
RUN mvn clean package -DskipTests
RUN ls -al /home/maven/QuadLingo/target/  # Debugging step to list contents of the target directory

# Use OpenJDK to run the application
FROM openjdk:17-jdk
COPY --from=builder /home/maven/QuadLingo/target/QuadLingo-1.0-SNAPSHOT.jar /QuadLingo.jar
ENTRYPOINT ["java", "-jar", "/QuadLingo.jar"]
