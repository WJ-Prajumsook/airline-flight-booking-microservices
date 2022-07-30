package org.wj.prajumsook.booking.resource;

import org.junit.jupiter.api.Test;
import org.wj.prajumsook.booking.model.Airplane;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;

@QuarkusTest
@QuarkusTestResource(TestContainerResource.class)
public class AirplaneResourceTest {

  @Test
  public void testCreateAndFind() {

    Airplane airplane = new Airplane()
        .setName("Test name")
        .setIataCode("IATA-CODE")
        .setIcaoCode("ICAO");

    Airplane response = given().when()
        .contentType(ContentType.JSON)
        .body(airplane)
        .post("/api/airplanes/v1")
        .then()
        .statusCode(200)
        .body(containsString("Test name"))
        .extract()
        .as(Airplane.class);

    given().when()
        .get("/api/airplanes/v1/" + response.getId())
        .then()
        .statusCode(200)
        .body(containsString("Test name"));
  }

}
