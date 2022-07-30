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

import org.wj.prajumsook.booking.entity.CountryEntity;
import org.wj.prajumsook.booking.model.Country;
import org.wj.prajumsook.booking.repository.CountryRepository;

@ApplicationScoped
@Transactional
public class CountryService {

  @Inject
  CountryRepository repository;

  public Country findById(Long id) {
    return repository.findByIdOptional(id)
        .map(CountryService::mapToDomain)
        .orElseThrow(() -> new WebApplicationException("Country id: " + id + " not found", 404));
  }

  public Country findByName(String name) {
    return repository.find("LOWER(name) = LOWER(?1)", name).firstResultOptional()
        .map(CountryService::mapToDomain)
        .orElseThrow(() -> new WebApplicationException("Country name: " + name + " not found", 404));
  }

  public Country create(Country country) {
    CountryEntity entity = mapToEntity(country);
    repository.persistAndFlush(entity);

    return mapToDomain(entity);
  }

  public Country delete(Long id) {
    var entity = repository.findByIdOptional(id)
        .orElseThrow(() -> new WebApplicationException("Country id: " + id + " not found", 404));

    repository.delete(entity);

    return mapToDomain(entity);
  }

  public void initData() {
    Pattern pattern = Pattern.compile((","));
    try {
      InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("country.dat");
      Stream<String> lines = new BufferedReader(new InputStreamReader(in)).lines();
      lines.forEach(line -> {
        String[] item = pattern.split(line);
        CountryEntity entity = new CountryEntity()
            .setName(item[0])
            .setIsoCode(item[1])
            .setDafifCode(item[2]);
        repository.persist(entity);
      });
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public static Country mapToDomain(CountryEntity entity) {
    return new ObjectMapper().convertValue(entity, Country.class);
  }

  public static CountryEntity mapToEntity(Country country) {
    return new ObjectMapper().convertValue(country, CountryEntity.class);
  }
}
