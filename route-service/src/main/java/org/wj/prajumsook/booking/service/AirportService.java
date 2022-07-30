package org.wj.prajumsook.booking.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.WebApplicationException;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.wj.prajumsook.booking.entity.AirportEntity;
import org.wj.prajumsook.booking.model.Airport;
import org.wj.prajumsook.booking.repository.AirportRepository;

@ApplicationScoped
@Transactional
public class AirportService {

  @Inject
  AirportRepository repository;
  @Inject
  CountryService countryService;

  public Airport findById(Long id) {
    return repository.findByIdOptional(id)
        .map(a -> createResponse(a))
        .orElseThrow(() -> new WebApplicationException("Airport id: " + id + " not found", 404));
  }

  public Airport create(Airport airport) {
    AirportEntity entity = mapToEntity(airport);
    entity.setCountryName(airport.getCountry().getName());
    repository.persistAndFlush(entity);

    return createResponse(entity);
  }

  public Airport delete(Long id) {
    var entity = repository.findByIdOptional(id)
        .orElseThrow(() -> new WebApplicationException("Airport id: " + id + " not found", 404));
    repository.delete(entity);

    return createResponse(entity);
  }

  private Airport createResponse(AirportEntity entity) {
    Airport airport = mapToDomain(entity);
    airport.setCountry(countryService.findByName(entity.getCountryName()));

    return airport;
  }

  public void initData() {
    Pattern pattern = Pattern.compile(",");
    try {
      InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("airport.dat");
      Stream<String> lines = new BufferedReader(new InputStreamReader(in)).lines();
      lines.forEach(line -> {
        String[] item = pattern.split(line);
        AirportEntity entity = new AirportEntity()
            .setAirportId(Long.parseLong(item[0]))
            .setName(item[1])
            .setCity(item[2])
            .setCountryName(item[3])
            .setIata(item[4])
            .setIcao(item[5])
            .setLatitude(item[6])
            .setLongitude(item[7])
            .setAltitude(item[8])
            .setTimezone(item[9])
            .setDst(item[10])
            .setTzDatabase(item[11])
            .setType(item[12])
            .setSource(item[13]);

        repository.persist(entity);
      });
    } catch (Exception ex) {
      ex.printStackTrace();
      throw new WebApplicationException(ex.getMessage(), 500);
    }
  }

  public static Airport mapToDomain(AirportEntity entity) {
    return new ObjectMapper().convertValue(entity, Airport.class);
  }

  public static AirportEntity mapToEntity(Airport airport) {
    return new ObjectMapper().convertValue(airport, AirportEntity.class);
  }
}
