package org.wj.prajumsook.booking.resource;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.wj.prajumsook.booking.model.BoardingPass;
import org.wj.prajumsook.booking.model.Booking;
import org.wj.prajumsook.booking.model.Flight;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;

@QuarkusTest
@QuarkusTestResource(TestContainerResource.class)
public class BoardingPassResourceTest {

  @Test
  public void testCreateAndFind() {

    Flight flight = new Flight()
        .setFlightNumber("ABC123")
        .setRouteId(4242L);
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
    booking = given().when()
        .contentType(ContentType.JSON)
        .body(booking)
        .post("/api/bookings/v1")
        .then()
        .statusCode(200)
        .extract()
        .as(Booking.class);

    BoardingPass boardingPass = new BoardingPass()
        .setBooking(booking)
        .setGate("GATE00")
        .setQrCode("QR-CODE")
        .setTerminal("TE-00")
        .setScheduledTime("22-22-2222:22:22");
    boardingPass = given().when()
        .contentType(ContentType.JSON)
        .body(boardingPass)
        .post("/api/boarding_passes/v1")
        .then()
        .statusCode(200)
        .extract()
        .as(BoardingPass.class);

    assertEquals("confirmed", boardingPass.getBooking().getStatus());
    assertEquals("ABC123", boardingPass.getBooking().getFlight().getFlightNumber());
    assertEquals("QR-CODE", boardingPass.getQrCode());
  }
}
