package org.wj.prajumsook.booking.resource;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.wj.prajumsook.booking.model.Flight;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;

@QuarkusTest
@QuarkusTestResource(TestContainerResource.class)
public class FlightResourceTest {

  @Test
  public void testCreateAndFind() {
    Flight flight = new Flight()
        .setFlightNumber("ABC123")
        .setRouteId(4242L);

    Flight response = given().when()
        .contentType(ContentType.JSON)
        .body(flight)
        .post("/api/flights/v1")
        .then()
        .statusCode(200)
        .extract()
        .as(Flight.class);

    assertEquals("ABC123", response.getFlightNumber());
    assertEquals(4242, response.getRouteId());
  }
}
