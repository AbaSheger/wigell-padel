FROM openjdk:17-jdk-alpine
COPY target/wigell-padel-0.0.1-SNAPSHOT.jar wigell-padel.jar
ENTRYPOINT ["java","-jar","/wigell-padel.jar"]