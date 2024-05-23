FROM openjdk:17-jdk-slim-buster
COPY build/libs/NewsNBA-0.0.1-SNAPSHOT.jar /news.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar","news.jar"]