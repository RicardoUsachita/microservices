FROM openjdk:17
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} acopioImport-0.0.1-SNAPSHOT.jar
EXPOSE 8090
ENTRYPOINT ["java","-jar","/acopioImport-0.0.1-SNAPSHOT.jar"]