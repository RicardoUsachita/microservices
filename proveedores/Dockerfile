FROM openjdk:17
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} proveedores-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/proveedores-0.0.1-SNAPSHOT.jar"]