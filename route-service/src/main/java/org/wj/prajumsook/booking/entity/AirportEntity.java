package org.wj.prajumsook.booking.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Table(name = "airports")
@Setter
@Getter
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AirportEntity {

  @Id
  private Long airportId;

  private String name;
  private String city;
  private String countryName;
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
