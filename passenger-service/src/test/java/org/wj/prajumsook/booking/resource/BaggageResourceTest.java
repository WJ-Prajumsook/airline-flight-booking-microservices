package org.wj.prajumsook.booking.resource;

import org.junit.jupiter.api.Test;
import org.wj.prajumsook.booking.model.Baggage;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;

@QuarkusTest
@QuarkusTestResource(TestContainerResource.class)
public class BaggageResourceTest {

  @Test
  public void testCreateAndFind() {

    Baggage baggage = new Baggage()
        .setWeightInKg(23.99)
        .setBookingId(123L);

    baggage = given().when()
        .contentType(ContentType.JSON)
        .body(baggage)
        .post("/api/baggages/v1")
        .then()
        .statusCode(200)
        .extract()
        .as(Baggage.class);
  }
}
