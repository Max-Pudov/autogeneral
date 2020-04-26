FROM openjdk:11-jdk-slim

RUN mkdir /app

COPY target/autogeneral.jar /app

EXPOSE 8080

CMD ["sh", "-c", "java -jar /app/autogeneral.jar"]