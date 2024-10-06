FROM maven:latest

# set the working directory in the container
WORKDIR /app

# copy the pom.xml file to the container
COPY pom.xml /app/

# copy the entire project to the container
COPY . /app/

# package the application
RUN mvn package

# run the main class of the application
CMD ["java", "-jar", "target/Quadlingo.jar"]