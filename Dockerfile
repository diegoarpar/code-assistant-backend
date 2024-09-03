# Use the official Gradle image as the base image
FROM gradle:8.9.0-jdk21

# Set the working directory
WORKDIR /app

# Copy the Gradle wrapper and project files to the container
COPY . .

# Run the Gradle build
RUN gradle build --no-daemon

# Set the default command to run the application
CMD ["gradle", "run", "--no-daemon"]