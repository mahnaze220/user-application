# USER APPLICATION DOCKERFILE
# --------------------------
# This Dockerfile extends the openjdk:8 image by creating User Application.
#
# HOW TO BUILD THIS IMAGE
# -----------------------
# Run: 
#      $ sudo docker build -t user-application:1.0 .
#      $ sudo docker run -d -p 8080:8080 -t user-application:1.0

#
# Pull base image
# ---------------
FROM maven:3.5.2-jdk-8-alpine AS MAVEN_BUILD

# Configuration
# ----------------------------------------------------------

COPY pom.xml /build/
COPY src /build/src/

WORKDIR /build/

RUN mvn package clean install

FROM openjdk:8-jre-alpine

WORKDIR /app

COPY --from=MAVEN_BUILD /build/target/user-application-1.0.jar /app/

ENTRYPOINT ["java", "-jar", "user-application-1.0.jar"]