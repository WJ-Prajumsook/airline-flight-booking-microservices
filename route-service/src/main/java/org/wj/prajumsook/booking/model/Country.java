package org.wj.prajumsook.booking.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Country {

  private Long id;
  private String name;
  private String isoCode;
  private String dafifCode;

}
