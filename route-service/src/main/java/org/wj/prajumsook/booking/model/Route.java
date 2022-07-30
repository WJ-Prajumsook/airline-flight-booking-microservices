package org.wj.prajumsook.booking.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Route {

  private Long id;
  private Airline airline;
  private Airport sourceAirport;
  private Airport destinationAirport;
  private String codeshare;
  private String stops;
  private Airplane airplane;

}
