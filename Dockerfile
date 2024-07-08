# Use the official OpenJDK image as the base image
FROM openjdk:21-jdk

# Set the working directory
WORKDIR /app

# Copy the Maven project files to the container
COPY . .

# Build the application
RUN ./mvnw clean package -DskipTests

# Expose the port the application runs on
EXPOSE 8081

# Set the entry point to run the application
ENTRYPOINT ["java", "-jar", "target/ProductService-0.0.1.jar"]
