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

import org.wj.prajumsook.booking.entity.RouteEntity;
import org.wj.prajumsook.booking.model.Route;
import org.wj.prajumsook.booking.repository.RouteRepository;

@ApplicationScoped
@Transactional
public class RouteService {

  @Inject
  RouteRepository routeRepository;
  @Inject
  AirportService airportService;
  @Inject
  AirlineService airlineService;
  @Inject
  AirplaneService airplaneService;

  public Route findById(Long id) {
    return routeRepository.findByIdOptional(id)
        .map(r -> createResponse(r))
        .orElseThrow(() -> new WebApplicationException("Route id: " + id + " not found", 404));
  }

  private Route createResponse(RouteEntity entity) {
    Route route = mapToDomain(entity);
    route.setAirline(airlineService.findById(entity.getAirlineId()));
    route.setSourceAirport(airportService.findById(entity.getSourceAirportId()));
    route.setDestinationAirport(airportService.findById(entity.getDestinationAirportId()));
    if (entity.getAirplaneCode() != null) {
      route.setAirplane(airplaneService.findByIATA(entity.getAirplaneCode()));
    }
    return route;
  }

  public void initData() {
    Pattern pattern = Pattern.compile(",");
    try {
      InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("route.dat");
      Stream<String> lines = new BufferedReader(new InputStreamReader(in)).lines();
      lines.forEach(line -> {
        String[] item = pattern.split(line);
        RouteEntity entity = new RouteEntity()
            .setAirlineICAO(item[0])
            .setAirlineId(Long.parseLong(item[1]))
            .setSourceAirportIATA(item[2])
            .setSourceAirportId(Long.parseLong(item[3]))
            .setDestinationAirportIATA(item[4])
            .setDestinationAirportId(Long.parseLong(item[5]))
            .setCodeshare(item[6])
            .setStops(item[7]);
        if (item.length > 8) {
          entity.setAirplaneCode(item[8]);
        }
        routeRepository.persist(entity);
      });
    } catch (Exception ex) {
      ex.printStackTrace();
      throw new WebApplicationException(ex.getMessage(), 500);
    }
  }

  public static Route mapToDomain(RouteEntity entity) {
    return new ObjectMapper().convertValue(entity, Route.class);
  }

  public static RouteEntity mapToEntity(Route route) {
    return new ObjectMapper().convertValue(route, RouteEntity.class);
  }
}
