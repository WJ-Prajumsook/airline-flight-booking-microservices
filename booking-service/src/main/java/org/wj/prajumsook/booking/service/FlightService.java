package org.wj.prajumsook.booking.service;

import java.util.Set;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.WebApplicationException;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.wj.prajumsook.booking.entity.FlightEntity;
import org.wj.prajumsook.booking.model.Flight;
import org.wj.prajumsook.booking.repository.FlightRepository;

@ApplicationScoped
@Transactional
public class FlightService {

  @Inject
  FlightRepository repository;

  public Set<Flight> findAll() {
    return repository.findAll().stream()
        .map(FlightService::mapToDomain)
        .collect(Collectors.toSet());
  }

  public Flight findById(Long id) {
    return repository.findByIdOptional(id)
        .map(FlightService::mapToDomain)
        .orElseThrow(() -> new WebApplicationException("Flight id: " + id + " not found", 404));
  }

  public Flight findByRouteId(Long id) {
    return repository.find("routeid", id).firstResultOptional()
        .map(FlightService::mapToDomain)
        .orElseThrow(() -> new WebApplicationException("Flight with route id: " + id + " not found", 404));
  }

  public Flight create(Flight flight) {
    FlightEntity entity = mapToEntity(flight);
    repository.persistAndFlush(entity);
    return mapToDomain(entity);
  }

  public Flight delete(Long id) {
    var entity = repository.findByIdOptional(id)
        .orElseThrow(() -> new WebApplicationException("Flight id: " + id + " not found", 404));
    repository.delete(entity);
    return mapToDomain(entity);
  }

  public static Flight mapToDomain(FlightEntity entity) {
    return new ObjectMapper().convertValue(entity, Flight.class);
  }

  public static FlightEntity mapToEntity(Flight flight) {
    return new ObjectMapper().convertValue(flight, FlightEntity.class);
  }

}
