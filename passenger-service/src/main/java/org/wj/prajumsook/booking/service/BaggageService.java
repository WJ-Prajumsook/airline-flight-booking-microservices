package org.wj.prajumsook.booking.service;

import java.util.Set;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.WebApplicationException;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.wj.prajumsook.booking.entity.BaggageEntity;
import org.wj.prajumsook.booking.model.Baggage;
import org.wj.prajumsook.booking.repository.BaggageRepository;

@ApplicationScoped
@Transactional
public class BaggageService {

  @Inject
  BaggageRepository repository;

  public Set<Baggage> findAll() {
    return repository.findAll().stream()
        .map(BaggageService::mapToDomain)
        .collect(Collectors.toSet());
  }

  public Baggage findById(Long id) {
    return repository.findByIdOptional(id)
        .map(BaggageService::mapToDomain)
        .orElseThrow(() -> new WebApplicationException("Baggage id:" + id + " not found", 404));
  }

  public Baggage create(Baggage baggage) {
    BaggageEntity entity = mapToEntity(baggage);
    repository.persistAndFlush(entity);
    return mapToDomain(entity);
  }

  public Baggage delete(Long id) {
    var entity = repository.findByIdOptional(id)
        .orElseThrow(() -> new WebApplicationException("Baggage id: " + id + " not found", 404));
    repository.delete(entity);

    return mapToDomain(entity);
  }

  public static Baggage mapToDomain(BaggageEntity entity) {
    return new ObjectMapper().convertValue(entity, Baggage.class);
  }

  public static BaggageEntity mapToEntity(Baggage baggage) {
    return new ObjectMapper().convertValue(baggage, BaggageEntity.class);
  }
}
