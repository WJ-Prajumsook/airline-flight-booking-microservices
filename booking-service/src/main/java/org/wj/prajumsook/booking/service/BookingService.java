package org.wj.prajumsook.booking.service;

import java.util.Set;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.WebApplicationException;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.wj.prajumsook.booking.entity.BookingEntity;
import org.wj.prajumsook.booking.model.Booking;
import org.wj.prajumsook.booking.repository.BookingRepository;

@ApplicationScoped
@Transactional
public class BookingService {

  @Inject
  BookingRepository repository;
  @Inject
  FlightService flightService;

  public Set<Booking> findAll() {
    return repository.findAll().stream()
        .map(b -> createResponse(b))
        .collect(Collectors.toSet());
  }

  public Booking findById(Long id) {
    return repository.findByIdOptional(id)
        .map(b -> createResponse(b))
        .orElseThrow(() -> new WebApplicationException("Booking id: " + id + " not found", 404));
  }

  public Booking create(Booking booking) {
    BookingEntity entity = mapToEntity(booking);
    entity.setFlightId(booking.getFlight().getId());
    repository.persistAndFlush(entity);

    return createResponse(entity);
  }

  public Booking delete(Long id) {
    var entity = repository.findByIdOptional(id)
        .orElseThrow(() -> new WebApplicationException("Booking id: " + id + " not found", 404));
    repository.delete(entity);

    return createResponse(entity);
  }

  private Booking createResponse(BookingEntity entity) {
    Booking booking = mapToDomain(entity);
    booking.setFlight(flightService.findById(entity.getFlightId()));

    return booking;
  }

  public static Booking mapToDomain(BookingEntity entity) {
    return new ObjectMapper().convertValue(entity, Booking.class);
  }

  public static BookingEntity mapToEntity(Booking booking) {
    return new ObjectMapper().convertValue(booking, BookingEntity.class);
  }
}
