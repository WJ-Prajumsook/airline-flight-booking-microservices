package org.wj.prajumsook.booking.service;

import java.util.Set;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import javax.ws.rs.WebApplicationException;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.wj.prajumsook.booking.entity.BoardingPassEntity;
import org.wj.prajumsook.booking.model.BoardingPass;
import org.wj.prajumsook.booking.repository.BoardingPassRepository;

@ApplicationScoped
@Transactional
public class BoardingPassService {

  @Inject
  BoardingPassRepository repository;
  @Inject
  BookingService bookingService;

  public Set<BoardingPass> findAll() {
    return repository.findAll().stream()
        .map(b -> createResponse(b))
        .collect(Collectors.toSet());
  }

  public BoardingPass findById(Long id) {
    return repository.findByIdOptional(id)
        .map(b -> createResponse(b))
        .orElseThrow(() -> new WebApplicationException("BoardingPass id: " + id + " not found", 404));
  }

  public BoardingPass create(BoardingPass boardingPass) {
    BoardingPassEntity entity = mapToEntity(boardingPass);
    entity.setBookingId(boardingPass.getBooking().getId());

    repository.persistAndFlush(entity);

    return createResponse(entity);
  }

  public BoardingPass delete(Long id) {
    var entity = repository.findByIdOptional(id)
        .orElseThrow(() -> new WebApplicationException("Boarding pass id: " + id + " not found", 404));
    repository.delete(entity);

    return createResponse(entity);
  }

  private BoardingPass createResponse(BoardingPassEntity entity) {
    BoardingPass boardingPass = mapToDomain(entity);
    boardingPass.setBooking(bookingService.findById(entity.getBookingId()));

    return boardingPass;
  }

  public static BoardingPass mapToDomain(BoardingPassEntity entity) {
    return new ObjectMapper().convertValue(entity, BoardingPass.class);
  }

  public static BoardingPassEntity mapToEntity(BoardingPass boardingPass) {
    return new ObjectMapper().convertValue(boardingPass, BoardingPassEntity.class);
  }
}
