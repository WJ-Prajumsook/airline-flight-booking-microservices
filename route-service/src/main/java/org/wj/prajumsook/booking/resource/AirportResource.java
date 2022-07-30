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
import org.wj.prajumsook.booking.model.Airport;
import org.wj.prajumsook.booking.service.AirportService;

@Path("/airports/v1")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AirportResource {

  @Inject
  AirportService airportService;

  @GET
  @Path("/{id}")
  public Airport findById(@RestPath Long id) {
    return airportService.findById(id);
  }

  @POST
  public Airport create(Airport airport) {
    return airportService.create(airport);
  }

  @DELETE
  @Path("/{id}")
  public Airport delete(@RestPath Long id) {
    return airportService.delete(id);
  }

  @GET
  @Path("/init")
  public Response initData() {
    airportService.initData();
    return Response.status(Response.Status.CREATED).build();
  }
}
