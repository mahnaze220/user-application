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
FROM openjdk:8-jdk-alpine

# Configuration
# ----------------------------------------------------------

# Refer to Maven build -> finalName
ARG JAR_FILE=/build/target/user-application-1.0.jar

# cd /opt/app
WORKDIR /opt/app

# cp /build/target/user-application-1.0.jar /opt/app/app.jar
COPY ${JAR_FILE} app.jar

# java -jar /opt/app/app.jar
ENTRYPOINT ["java","-jar","app.jar"]