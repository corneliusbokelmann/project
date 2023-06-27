# Use the base Docker image that supports Scala
FROM hseeberger/scala-sbt

# Set the working directory inside the container
WORKDIR /se

# Copy the project files to the container
COPY . /se

# Build your project inside the container
RUN sbt compile

# Expose any required ports
EXPOSE 8080

# Define the command to run your application
CMD ["sbt", "run"]