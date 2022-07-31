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

### Add dependencies to the project
To add Kubernetes, jib minikube and flyway dependencies I use this maven command.

```java
mvn quarkus:add-extension -Dextensions="kubernetes,jib,minikube,flyway"
```

To add hibernate-orm-panache, postgresql and openapi I use thid maven command.

```java
mvn quarkus:add-extension -Dextensions="hibernate-orm-panache,jdbc-postgresql,smallrye-openapi"
```

### Configure datasource
Add configuration settings properties in `application.properties` file.

```java
quarkus.datasource.db-kind=postgresql
quarkus.datasource.username=quarkus_user
quarkus.datasource.password=quarkus_pass
quarkus.datasource.jdbc.url=jdbc:postgresql://192.168.64.13:30831/route_db

quarkus.hibernate-orm.database.generation=update

%test.quarkus.flyway.migrate-at-start=true
```

[img-01]: media/img-01.png
[img-02]: media/img-02.png