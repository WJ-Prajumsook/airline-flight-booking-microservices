package org.wj.prajumsook.booking.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Flight {

  private Long id;
  private String flightNumber;
  private Long routeId;

}
