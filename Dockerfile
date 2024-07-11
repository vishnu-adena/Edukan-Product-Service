## Use the latest Ubuntu image as base
#FROM ubuntu:latest
#
## Install Java and Maven
#RUN apt-get update && apt-get install -y openjdk-21-jdk maven
#
## Set JAVA_HOME environment variable
#ENV JAVA_HOME /usr/lib/jvm/java-21-openjdk-amd64
#
## Add Java binaries to PATH
#ENV PATH $JAVA_HOME/bin:$PATH
#
## Set the working directory
#WORKDIR /app
#
## Copy the Maven project files to the container
#COPY . .
#
## Build the application
#RUN mvn clean package -DskipTests
#
## Expose the port the application runs on
#EXPOSE 8081
#
## Set the entry point to run the application
#ENTRYPOINT ["java", "-jar", "target/ProductService-0.0.1.jar"]
FROM openjdk:24-jdk-oraclelinux8

# Set the working directory in the container
WORKDIR /app

# Copy the local JAR file to the container
COPY target/ProductService-0.0.1-SNAPSHOT.jar app.jar

# Expose the port the application runs on
EXPOSE 8082

# Run the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]
