# user-application
A simple Spring Boot web application to provide REST user services which runs on a docker container.

Following tools and libraries are used to develop and test the application:
- Docker 
- Java 8 
- Spring Boot
- Maven
- H2 Database

How to run:
$ git clone https://github.com/mahnaze220/user-application
$ cd docker-spring-boot
$ mvn clean package
$ java -jar target/spring-boot-web.jar

  access http://localhost:8080

How to build and run docker image:

// create a docker image
$ sudo docker build -t user-application:1.0 .

//or get docker images from following repository
$ sudo docker pull mahnazebr/user-application

// run it
$ sudo docker run -d -p 8080:8080 -t user-application:1.0
