package org.wj.prajumsook.booking.resource;

import java.util.Set;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.reactive.RestPath;
import org.wj.prajumsook.booking.model.Flight;
import org.wj.prajumsook.booking.service.FlightService;

@Path("/flights/v1")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FlightResource {

  @Inject
  FlightService flightService;

  @GET
  public Set<Flight> findAll() {
    return flightService.findAll();
  }

  @GET
  @Path("/{id}")
  public Flight findById(@RestPath Long id) {
    return flightService.findById(id);
  }

  @POST
  public Flight create(Flight flight) {
    return flightService.create(flight);
  }

  @DELETE
  @Path("/{id}")
  public Flight delete(@RestPath Long id) {
    return flightService.delete(id);
  }
}
