# PizzappApi

## Running the Application with Docker
This section guides you through the process of building a Docker image for the application and running it in a container.

Prerequisites
Docker installed on your system.
Docker Daemon running.
Steps to Build and Run
Build the Docker Image

Navigate to the directory containing the Dockerfile and run the following command to build your Docker image:

    docker build -t my-application .

Replace my-application with your desired image name. This command builds a Docker image named my-application using the Dockerfile in the current directory.

Run the Image in a Container

To run your application on port 8081, use the following command:

    docker run -p 8081:8081 my-application

This command starts a new container from the my-application image. The -p 8081:8081 option maps the container's port 8081 to port 8081 on your host machine. This means if your application inside the container listens on port 8081, it will be accessible on your host machine's port 8081.
