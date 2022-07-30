package org.wj.prajumsook.booking.resource;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.reactive.RestPath;
import org.wj.prajumsook.booking.model.Airline;
import org.wj.prajumsook.booking.service.AirlineService;

@Path("/airlines/v1")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AirlineResource {

  @Inject
  AirlineService airlineService;

  @GET
  @Path("/{id}")
  public Airline findById(@RestPath Long id) {
    return airlineService.findById(id);
  }

  @POST
  public Airline create(Airline airline) {
    return airlineService.create(airline);
  }

  @DELETE
  @Path("/{id}")
  public Airline delete(@RestPath Long id) {
    return airlineService.delete(id);
  }

  @GET
  @Path("/init")
  public Response initData() {
    airlineService.initData();
    return Response.status(Response.Status.CREATED).build();
  }
}
