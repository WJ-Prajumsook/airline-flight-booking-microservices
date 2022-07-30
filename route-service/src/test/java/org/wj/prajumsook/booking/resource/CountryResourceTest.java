package org.wj.prajumsook.booking.resource;

import org.junit.jupiter.api.Test;
import org.wj.prajumsook.booking.model.Country;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;

@QuarkusTest
@QuarkusTestResource(TestContainerResource.class)
public class CountryResourceTest {

  @Test
  public void testFindById() {
    Country country = new Country()
        .setName("Test name")
        .setIsoCode("AA")
        .setDafifCode("ZZ");

    Country response = given().when()
        .contentType(ContentType.JSON)
        .body(country)
        .post("/api/countries/v1")
        .then()
        .statusCode(200)
        .extract()
        .as(Country.class);

    given().when()
        .get("/api/countries/v1/" + response.getId())
        .then()
        .statusCode(200)
        .body(containsString("Test name"));
  }
}
