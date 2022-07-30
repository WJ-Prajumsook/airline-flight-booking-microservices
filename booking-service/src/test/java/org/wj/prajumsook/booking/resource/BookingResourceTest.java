package org.wj.prajumsook.booking.resource;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

import org.junit.jupiter.api.Test;
import org.wj.prajumsook.booking.model.Booking;
import org.wj.prajumsook.booking.model.Flight;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

import java.util.Collections;

@QuarkusTest
@QuarkusTestResource(TestContainerResource.class)
public class BookingResourceTest {

  @Test
  public void testFindAllEmpty() {
    given()
        .when().get("/api/bookings/v1")
        .then()
        .statusCode(200);
  }

  @Test
  public void testCreateAndFind() {

    Flight flight = new Flight()
        .setRouteId(1234L)
        .setFlightNumber("ABC123");
    flight = given().when()
        .contentType(ContentType.JSON)
        .body(flight)
        .post("/api/flights/v1")
        .then()
        .statusCode(200)
        .extract()
        .as(Flight.class);

    Booking booking = new Booking()
        .setFlight(flight)
        .setStatus("confirmed")
        .setPassengerId(123L);

    given().when()
        .contentType(ContentType.JSON)
        .body(booking)
        .post("/api/bookings/v1")
        .then()
        .statusCode(200);
  }

}
