# Airline flight booking microservices with Quarkus
Airline flight booking microservices using Quarkus, hibernate-orm and PostgreSQL

### Table of Contents
- [Create Quarkus project](#create-quarkus-project-using-maven)
- [Add dependencies](#add-dependencies-to-the-project)
- [Configure datasource](#configure-datasource)
- [Defining entities](#defining-entities)
- [Defining repositories](#defining-repositories)
- [Defining domain models](#defining-domain-models)
- [Defining the services](#defining-the-services)
- [JAX-RS resources](#jax-rs-resources)
- [Identifies the application path](#identifies-the-application-path)
- [JAX-RS Exception mapper](#jax-rs-exception-mapper-provicer)
- [Test Container Resource](#test-container-resource)
- [JUnit Test](#junit-test)
- [Loading data from file](#loading-data-from-file)
- [Mapping object](#mapping-from-entity-object-to-domain-object)
- [Deploy service to Kubernetes](#running-services-on-kubernetes)


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

### Defining entities
I will use the `Repository pattern` to define my entities.

Ex:
```java
package org.wj.prajumsook.booking.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Table(name = "routes")
@Setter
@Getter
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RouteEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String airlineICAO;
  private Long airlineId;
  private String sourceAirportIATA;
  private Long sourceAirportId;
  private String destinationAirportIATA;
  private Long destinationAirportId;
  private String codeshare;
  private String stops;
  private String airplaneCode;

}
```

### Defining repositories
Ex:
```java
package org.wj.prajumsook.booking.repository;

import javax.enterprise.context.ApplicationScoped;

import org.wj.prajumsook.booking.entity.RouteEntity;

import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class RouteRepository implements PanacheRepository<RouteEntity> {
}
```

### Defining domain-models
Ex:
```java
package org.wj.prajumsook.booking.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Route {

  private Long id;
  private Airline airline;
  private Airport sourceAirport;
  private Airport destinationAirport;
  private String codeshare;
  private String stops;
  private Airplane airplane;

}
```

### Definning the services
Ex:
```java
package org.wj.prajumsook.booking.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.WebApplicationException;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.wj.prajumsook.booking.entity.RouteEntity;
import org.wj.prajumsook.booking.model.Route;
import org.wj.prajumsook.booking.repository.RouteRepository;

@ApplicationScoped
@Transactional
public class RouteService {

  @Inject
  RouteRepository routeRepository;
  @Inject
  AirportService airportService;
  @Inject
  AirlineService airlineService;
  @Inject
  AirplaneService airplaneService;

  public Route findById(Long id) {
    return routeRepository.findByIdOptional(id)
        .map(r -> createResponse(r))
        .orElseThrow(() -> new WebApplicationException("Route id: " + id + " not found", 404));
  }

  private Route createResponse(RouteEntity entity) {
    Route route = mapToDomain(entity);
    route.setAirline(airlineService.findById(entity.getAirlineId()));
    route.setSourceAirport(airportService.findById(entity.getSourceAirportId()));
    route.setDestinationAirport(airportService.findById(entity.getDestinationAirportId()));
    if (entity.getAirplaneCode() != null) {
      route.setAirplane(airplaneService.findByIATA(entity.getAirplaneCode()));
    }
    return route;
  }

  public static Route mapToDomain(RouteEntity entity) {
    return new ObjectMapper().convertValue(entity, Route.class);
  }

  public static RouteEntity mapToEntity(Route route) {
    return new ObjectMapper().convertValue(route, RouteEntity.class);
  }
}
```

### JAX-RS resources
Ex:
```java
package org.wj.prajumsook.booking.resource;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.reactive.RestPath;
import org.wj.prajumsook.booking.model.Route;
import org.wj.prajumsook.booking.service.RouteService;

@Path("/routes/v1")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RouteResource {

  @Inject
  RouteService routeService;

  @GET
  @Path("/{id}")
  public Route findById(@RestPath Long id) {
    return routeService.findById(id);
  }

  @GET
  @Path("/init")
  public Response initData() {
    routeService.initData();
    return Response.status(Response.Status.CREATED).build();
  }

}
```

### Identifies the application path
```java
package org.wj.prajumsook.booking;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/api")
public class RouteServiceApplication extends Application {
}
```

### JAX-RS exception mapper provider
EX:
```java
package org.wj.prajumsook.booking.service;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Provider
public class ErrorMapper implements ExceptionMapper<Exception> {

  @Override
  public Response toResponse(Exception ex) {
    int statusCode = Response.Status.BAD_REQUEST.getStatusCode();
    if (ex instanceof WebApplicationException) {
      statusCode = ((WebApplicationException) ex).getResponse().getStatus();
    }

    ObjectMapper mapper = new ObjectMapper();
    ObjectNode error = mapper.createObjectNode();
    error.put("exceptionType", ex.getClass().getName());
    error.put("statusCode", statusCode);
    error.put("error", (ex.getMessage() != null) ? ex.getMessage() : "unknown error");

    return Response.status(statusCode).entity(error).build();
  }
}
```

### Test container resource
```java
package org.wj.prajumsook.booking.resource;

import java.util.Collections;
import java.util.Map;

import org.testcontainers.containers.PostgreSQLContainer;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;

public class TestContainerResource implements QuarkusTestResourceLifecycleManager {

  private static final PostgreSQLContainer<?> db = new PostgreSQLContainer<>("postgres:13")
      .withDatabaseName("route_db")
      .withUsername("quarkus_user")
      .withPassword("quarkus_pass");

  @Override
  public Map<String, String> start() {
    db.start();
    return Collections.singletonMap("quarkus.datasource.jdbc.url", db.getJdbcUrl());
  }

  @Override
  public void stop() {
    db.stop();
  }
}
```

### JUnit test
```java
package org.wj.prajumsook.booking.resource;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.wj.prajumsook.booking.model.Airport;
import org.wj.prajumsook.booking.model.Country;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;

@QuarkusTest
@QuarkusTestResource(TestContainerResource.class)
public class AirportResourceTest {

  @Test
  public void testCreateAndFind() {

    Country country = given().when()
        .contentType(ContentType.JSON)
        .body(
            new Country()
                .setName("Greece")
                .setIsoCode("GR")
                .setDafifCode("GR"))
        .post("/api/countries/v1")
        .then()
        .statusCode(200).extract().as(Country.class);

    Airport airport = given().when()
        .contentType(ContentType.JSON)
        .body(
            new Airport()
                .setAirportId(1456L)
                .setAltitude("26")
                .setCountry(country)
                .setDst("E")
                .setIata("KLX")
                .setIcao("LGKL")
                .setLatitude("37.06829833984375")
                .setLongitude("22.02549934387207")
                .setName("Kalamata Airport")
                .setSource("OurAirports")
                .setTimezone("2")
                .setType("airport")
                .setTzDatabase("Europe/Athens"))
        .post("/api/airports/v1")
        .then()
        .body(containsString("Kalamata Airport"))
        .statusCode(200).extract().as(Airport.class);

    assertEquals("KLX", airport.getIata());
  }

}
```

### Loading data from file
This is the method that load data from file located in the rousrces directory to database.

```java
  public void initData() {
    Pattern pattern = Pattern.compile(",");
    try {
      InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("route.dat");
      Stream<String> lines = new BufferedReader(new InputStreamReader(in)).lines();
      lines.forEach(line -> {
        String[] item = pattern.split(line);
        RouteEntity entity = new RouteEntity()
            .setAirlineICAO(item[0])
            .setAirlineId(Long.parseLong(item[1]))
            .setSourceAirportIATA(item[2])
            .setSourceAirportId(Long.parseLong(item[3]))
            .setDestinationAirportIATA(item[4])
            .setDestinationAirportId(Long.parseLong(item[5]))
            .setCodeshare(item[6])
            .setStops(item[7]);
        if (item.length > 8) {
          entity.setAirplaneCode(item[8]);
        }
        routeRepository.persist(entity);
      });
    } catch (Exception ex) {
      ex.printStackTrace();
      throw new WebApplicationException(ex.getMessage(), 500);
    }
  }
```

### Mapping from entity object to domain object
Using `Jackson`
```java
  public static Route mapToDomain(RouteEntity entity) {
    return new ObjectMapper().convertValue(entity, Route.class);
  }
```
And from domain to entity
```java
  public static RouteEntity mapToEntity(Route route) {
    return new ObjectMapper().convertValue(route, RouteEntity.class);
  }
```

### Running services on Kubernetes
To run services on kubernetes on my local I use this command when you are inside the quarkus project.
```java
mvn clean package -Dquarkus.kubernetes.deploy=true
```

And to undeploy just run:
```java
kubectl delete -f target/kubernetes/minikube.yaml
```




[img-01]: media/img-01.png
[img-02]: media/img-02.png