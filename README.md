# Airline flight booking microservices with Quarkus
Airline flight booking microservices using Quarkus, hibernate-orm and PostgreSQL

# Video
[![Video](https://img.youtube.com/vi/6VDaBl0Nvs8/maxresdefault.jpg)](https://youtu.be/6VDaBl0Nvs8)

# Quarkus
A Kubernetes Native Java stack tailored. Find out more info here [Quarkus.io](https://quarkus.io/) 

# Hibernate-orm
Object/Relational Mapping [Hibernate-orm](https://hibernate.org/orm/)

# PostgreSQL
The world's most advanced open source relational database [PostgreSQL](https://www.postgresql.org/)

# Testcontainers
Testcontainers is a Java library that supports JUnit tests, providing lightweight, throwaway instances of common databases, Selenium web browsers, or anything else that can run in a Docker container. [Testcontainers](https://www.testcontainers.org/)

## Project layout
![img-01]

## Package layout
![img-02]

### Create Quarkus project using maven
```java
mvn io.quarkus:quarkus-maven-plugin:2.8.1.Final:create \
 	-DprojectGroupId=prajumsook \
	-DprojectArtifactId=passenger-service \
	-DclassName="org.wj.prajumsook.booking.resource.PassengerResource" \
	-Dpath="/passengers"
```




[img-01]: media/img-01.png
[img-02]: media/img-02.png