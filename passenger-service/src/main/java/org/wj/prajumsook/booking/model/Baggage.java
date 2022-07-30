package org.wj.prajumsook.booking.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Baggage {

  private Long id;
  private Double weightInKg;
  private Long bookingId;

}
