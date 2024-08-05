FROM openjdk:17-jdk-slim

VOLUME /tmp
COPY . /app
WORKDIR /app
RUN chmod +x ./mvnw
RUN ./mvnw clean package
ENTRYPOINT ["java", "-jar", "/app/target/hive-test-0.0.1-SNAPSHOT.jar"]
