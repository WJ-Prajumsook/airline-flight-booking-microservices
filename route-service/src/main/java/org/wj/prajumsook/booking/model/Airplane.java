package org.wj.prajumsook.booking.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Airplane {

  private Long id;
  private String name;
  private String iataCode;
  private String icaoCode;

}
