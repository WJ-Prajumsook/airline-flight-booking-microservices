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
import org.wj.prajumsook.booking.model.Baggage;
import org.wj.prajumsook.booking.service.BaggageService;

@Path("/baggages/v1")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BaggageResource {

  @Inject
  BaggageService baggageService;

  @GET
  public Set<Baggage> findAll() {
    return baggageService.findAll();
  }

  @GET
  @Path("/{id}")
  public Baggage findById(@RestPath Long id) {
    return baggageService.findById(id);
  }

  @POST
  public Baggage create(Baggage baggage) {
    return baggageService.create(baggage);
  }

  @DELETE
  @Path("/{id}")
  public Baggage delete(@RestPath Long id) {
    return baggageService.delete(id);
  }
}
