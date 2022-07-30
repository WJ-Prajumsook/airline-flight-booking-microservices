package org.wj.prajumsook.booking.service;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.WebApplicationException;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.wj.prajumsook.booking.entity.PassengerEntity;
import org.wj.prajumsook.booking.model.Passenger;
import org.wj.prajumsook.booking.repository.PassengerRepository;

@ApplicationScoped
@Transactional
public class PassengerService {

  @Inject
  PassengerRepository repository;
  @Inject
  BaggageService baggageService;

  public Passenger findById(Long id) {
    return repository.findByIdOptional(id)
        .map(p -> createResponse(p))
        .orElseThrow(() -> new WebApplicationException("Passenger id: " + id + " not found", 404));
  }

  public Passenger create(Passenger passenger) {
    PassengerEntity entity = mapToEntity(passenger);
    entity.setBaggageId(passenger.getBaggage().getId());
    repository.persistAndFlush(entity);

    return createResponse(entity);
  }

  public Passenger delete(Long id) {
    var passenger = repository.findByIdOptional(id)
        .orElseThrow(() -> new WebApplicationException("Passenger id: " + id + " not found", 404));
    repository.delete(passenger);

    return createResponse(passenger);
  }

  private Passenger createResponse(PassengerEntity entity) {
    Passenger passenger = mapToDomain(entity);
    passenger.setBaggage(baggageService.findById(entity.getBaggageId()));
    return passenger;
  }

  public static Passenger mapToDomain(PassengerEntity entity) {
    return new ObjectMapper().convertValue(entity, Passenger.class);
  }

  public static PassengerEntity mapToEntity(Passenger passenger) {
    return new ObjectMapper().convertValue(passenger, PassengerEntity.class);
  }
}
