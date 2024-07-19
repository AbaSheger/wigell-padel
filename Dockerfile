FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY target/wigell-padel-0.0.1-SNAPSHOT.jar wigell-padel.jar
COPY src/main/resources/log4j.properties /app/log4j.properties
VOLUME ["/logs"]
ENTRYPOINT ["java", "-jar", "/app/wigell-padel.jar"]
