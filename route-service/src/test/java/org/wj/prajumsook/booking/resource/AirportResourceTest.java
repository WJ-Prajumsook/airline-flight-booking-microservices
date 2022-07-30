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
