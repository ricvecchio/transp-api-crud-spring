FROM ubuntu:latest AS build

RUN apt-get update
RUN apt-get install openjdk-21-jdk -y
COPY . .

RUN apt-get install maven -y
RUN mvn clean install

FROM openjdk:21-jdk-slim

EXPOSE 8080

COPY --from=build /target/transp-api-crud-spring-0.0.1-SNAPSHOT.jar app.jar

ENV JAVA_HOME=C:\Program Files\Java\jdk-21
ENV PATH=$JAVA_HOME/bin:$PATH

ENTRYPOINT [ "java", "-jar", "app.jar" ]