FROM eclipse-temurin:21-jdk-jammy
VOLUME /tmp
COPY build/libs/person-api-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]