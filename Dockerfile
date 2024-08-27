FROM openjdk:11.0-jre-slim
EXPOSE 8081
ARG JAR_FILE=target/*.jar
ADD ${JAR_FILE} ms-account.jar
ENTRYPOINT ["java","-jar","/ms-account.jar"]