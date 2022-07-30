package org.wj.prajumsook.booking.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Airline {

  private Long airlineId;
  private String name;
  private String alias;
  private String iata;
  private String icao;
  private String callsign;
  private Country country;
  private String active;

}
