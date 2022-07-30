package org.wj.prajumsook.booking.resource;

import org.junit.jupiter.api.Test;
import org.wj.prajumsook.booking.model.Airline;
import org.wj.prajumsook.booking.model.Country;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;

@QuarkusTest
@QuarkusTestResource(TestContainerResource.class)
public class AirlineResourceTest {

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
        .then().statusCode(200).extract().as(Country.class);

    Airline airline = new Airline()
        .setAirlineId(96L)
        .setActive("Y")
        .setAlias("")
        .setCallsign("AEGEAN")
        .setCountry(country)
        .setIata("A3")
        .setIcao("AEGEAN")
        .setName("Aegean Airlines");

    given().when()
        .contentType(ContentType.JSON)
        .body(airline)
        .post("/api/airlines/v1")
        .then()
        .statusCode(200);
  }
}
