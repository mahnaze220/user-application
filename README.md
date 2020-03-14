# user-application
A simple Spring Boot web application to provide REST user services which runs on a docker container.

Provided REST services:

- create new user with contact data: /createUser
- return user by id: /createUser
- return user by name: /findUserByName
- add additional mail/phone data: addContactInfo
- update existing mail/phone data: updateContactInfo
- delete user: /deleteUser

--------------------------------------------------------

Following tools and libraries are used to develop and test the application:
- Docker 
- Java 8 
- Spring Boot
- Maven
- H2 Database

---------------------------------------------------------
How to run:

  $ git clone https://github.com/mahnaze220/user-application

  $ cd user-application

  $ mvn clean package

  $ java -jar target/user-application-1.0.jar

  access http://localhost:8080

---------------------------------------------------------  

How to build and run docker image:

  // create a docker image

  $ sudo docker build -t user-application:1.0 .


  // run it

  $ sudo docker run -d -p 8080:8080 -t user-application:1.0
