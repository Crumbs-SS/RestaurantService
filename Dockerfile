# parser directive (recommended)
# syntax=docker/dockerfile:1

#base image for our application
FROM openjdk:16-alpine3.13

#create a working directory
WORKDIR /app

#copy maven wrapper and pom.xml into our image
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

# install dependencies into the image
RUN ./mvnw dependency:go-offline

# add source code
COPY src ./src

# command to run when our image is executed inside a container
CMD ["./mvnw", "spring-boot:run"]

