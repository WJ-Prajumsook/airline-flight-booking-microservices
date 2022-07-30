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
import org.wj.prajumsook.booking.model.Airplane;
import org.wj.prajumsook.booking.service.AirplaneService;

@Path("/airplanes/v1")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AirplaneResource {

  @Inject
  AirplaneService airplaneService;

  @GET
  @Path("/{id}")
  public Airplane findById(@RestPath Long id) {
    return airplaneService.findById(id);
  }

  @GET
  @Path("/iata/{iata}")
  public Airplane findByIATA(@RestPath String iata) {
    return airplaneService.findByIATA(iata);
  }

  @POST
  public Airplane create(Airplane airplane) {
    return airplaneService.create(airplane);
  }

  @DELETE
  @Path("/{id}")
  public Airplane delete(@RestPath Long id) {
    return airplaneService.delete(id);
  }

  @GET
  @Path("/init")
  public Response initData() {
    airplaneService.initData();
    return Response.status(Response.Status.CREATED).build();
  }

}
