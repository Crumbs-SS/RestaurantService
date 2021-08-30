#Test Comment to trigger webhook #2
FROM openjdk:16-alpine3.13
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
CMD ["java","-jar","/app.jar"]
