package org.wj.prajumsook.booking.resource;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

import org.junit.jupiter.api.Test;
import org.wj.prajumsook.booking.model.Baggage;
import org.wj.prajumsook.booking.model.Passenger;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class PassengerResourceTest {

  @Test
  public void testCreateAndFind() {
    Baggage baggage = new Baggage()
        .setBookingId(123L)
        .setWeightInKg(23.89);
    baggage = given().when()
        .contentType(ContentType.JSON)
        .body(baggage)
        .post("/api/baggages/v1")
        .then()
        .statusCode(200)
        .extract()
        .as(Baggage.class);

    Passenger passenger = new Passenger()
        .setFirstName("Wj")
        .setLastName("lastname")
        .setDateOfBirth("22-22-2222")
        .setCitizenship("citizenship")
        .setResidence("residence")
        .setPassportNumber("1234567890")
        .setBaggage(baggage);

    passenger = given()
        .when()
        .contentType(ContentType.JSON)
        .body(passenger)
        .post("/api/passengers/v1")
        .then()
        .statusCode(200)
        .extract()
        .as(Passenger.class);
  }

}
