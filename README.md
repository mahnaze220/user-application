# user-application
A simple Spring Boot web application to provide REST user services which runs on a docker container.

Provided REST services:

- create new user with contact data: /user-controller/createUser
- return user by id: /user-controller/createUser
- return user by name: /user-controller/findUserByName
- add additional mail/phone data: /user-controller/addContactInfo
- update existing mail/phone data: /user-controller/updateContactInfo
- delete user: /user-controller/deleteUser

--------------------------------------------------------

Following tools and libraries are used to develop and test the application:
- Spring Boot 
- Java 8 
- Maven
- Docker
- H2 Database
- Swagger

---------------------------------------------------------
How to run:

  $ git clone https://github.com/mahnaze220/user-application

  $ cd user-application

  $ mvn clean package

  $ java -jar target/user-application-1.0.jar

  url: http://localhost:8080/swagger-ui.html
  
---------------------------------------------------------  

How to build and run docker image:

  // create a docker image

  $ sudo docker build -t user-application:1.0 .


  // run it

  $ sudo docker run -d -p 8080:8080 -t user-application:1.0
