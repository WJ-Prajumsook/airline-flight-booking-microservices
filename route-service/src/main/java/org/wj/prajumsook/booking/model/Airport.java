package org.wj.prajumsook.booking.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Airport {

  private Long airportId;
  private String name;
  private String city;
  private Country country;
  private String iata;
  private String icao;
  private String latitude;
  private String longitude;
  private String altitude;
  private String timezone;
  private String dst;
  private String tzDatabase;
  private String type;
  private String source;

}
