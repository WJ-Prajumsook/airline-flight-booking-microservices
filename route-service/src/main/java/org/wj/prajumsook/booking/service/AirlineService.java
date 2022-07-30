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

import org.wj.prajumsook.booking.entity.AirlineEntity;
import org.wj.prajumsook.booking.model.Airline;
import org.wj.prajumsook.booking.repository.AirlineRepository;

@ApplicationScoped
@Transactional
public class AirlineService {

  @Inject
  AirlineRepository repository;

  @Inject
  CountryService countryService;

  public Airline findById(Long id) {
    return repository.findByIdOptional(id)
        .map(a -> createResponse(a))
        .orElseThrow(() -> new WebApplicationException("Airline id: " + id + " not found", 404));
  }

  public Airline create(Airline airline) {
    AirlineEntity entity = mapToEntity(airline);
    entity.setCountryName(airline.getCountry().getName());
    repository.persistAndFlush(entity);

    return createResponse(entity);
  }

  public Airline delete(Long id) {
    var entity = repository.findByIdOptional(id)
        .orElseThrow(() -> new WebApplicationException("Airline id: " + id + " not found", 404));
    repository.delete(entity);

    return createResponse(entity);
  }

  private Airline createResponse(AirlineEntity entity) {
    Airline airline = mapToDomain(entity);
    airline.setCountry(countryService.findByName(entity.getCountryName()));

    return airline;
  }

  public void initData() {
    Pattern pattern = Pattern.compile(",");
    try {
      InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("airline.dat");
      Stream<String> lines = new BufferedReader(new InputStreamReader(in)).lines();
      lines.forEach(line -> {
        String[] item = pattern.split(line);
        AirlineEntity entity = new AirlineEntity()
            .setAirlineId(Long.parseLong(item[0]))
            .setName(item[1])
            .setAlias(item[2])
            .setIata(item[3])
            .setIcao(item[4])
            .setCallsign(item[5])
            .setCountryName(item[6])
            .setActive(item[7]);

        repository.persist(entity);
      });
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public static Airline mapToDomain(AirlineEntity entity) {
    return new ObjectMapper().convertValue(entity, Airline.class);
  }

  public static AirlineEntity mapToEntity(Airline airline) {
    return new ObjectMapper().convertValue(airline, AirlineEntity.class);
  }
}
