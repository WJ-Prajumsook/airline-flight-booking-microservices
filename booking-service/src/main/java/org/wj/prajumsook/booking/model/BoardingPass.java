package org.wj.prajumsook.booking.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class BoardingPass {

  private Long id;
  private String qrCode;
  private Booking booking;
  private String terminal;
  private String gate;
  private String scheduledTime;

}
