package org.wj.prajumsook.booking.resource;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.reactive.RestPath;
import org.wj.prajumsook.booking.model.Passenger;
import org.wj.prajumsook.booking.service.PassengerService;

@Path("/passengers/v1")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PassengerResource {

  @Inject
  PassengerService passengerService;

  @GET
  @Path("/{id}")
  public Passenger findById(@RestPath Long id) {
    return passengerService.findById(id);
  }

  @POST
  public Passenger create(Passenger passenger) {
    return passengerService.create(passenger);
  }

  @DELETE
  @Path("/{id}")
  public Passenger delete(@RestPath Long id) {
    return passengerService.delete(id);
  }
}
