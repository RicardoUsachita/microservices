FROM openjdk:17
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} valoresImport-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/valoresImport-0.0.1-SNAPSHOT.jar"]