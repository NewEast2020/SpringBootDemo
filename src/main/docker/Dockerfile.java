FROM openjdk:8-jdk-alpine
EXPOSE 8090
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} SpringDemoApp.jar
ENTRYPOINT ["java","-jar","/SpringDemoApp.jar"]