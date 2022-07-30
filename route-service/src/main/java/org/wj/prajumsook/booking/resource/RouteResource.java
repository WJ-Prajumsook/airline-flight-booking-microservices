package org.wj.prajumsook.booking.resource;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.reactive.RestPath;
import org.wj.prajumsook.booking.model.Route;
import org.wj.prajumsook.booking.service.RouteService;

@Path("/routes/v1")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RouteResource {

  @Inject
  RouteService routeService;

  @GET
  @Path("/{id}")
  public Route findById(@RestPath Long id) {
    return routeService.findById(id);
  }

  @GET
  @Path("/init")
  public Response initData() {
    routeService.initData();
    return Response.status(Response.Status.CREATED).build();
  }

}
