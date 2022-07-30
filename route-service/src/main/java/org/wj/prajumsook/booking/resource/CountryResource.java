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
import org.wj.prajumsook.booking.model.Country;
import org.wj.prajumsook.booking.service.CountryService;

@Path("/countries/v1")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CountryResource {

  @Inject
  CountryService countryService;

  @GET
  @Path("/{id}")
  public Country findById(@RestPath Long id) {
    return countryService.findById(id);
  }

  @GET
  @Path("/name/{name}")
  public Country findByName(@RestPath String name) {
    return countryService.findByName(name);
  }

  @POST
  public Country create(Country country) {
    return countryService.create(country);
  }

  @DELETE
  @Path("/{id}")
  public Country deletel(@RestPath Long id) {
    return countryService.delete(id);
  }

  @GET
  @Path("/init")
  public Response initData() {
    countryService.initData();
    return Response.status(Response.Status.CREATED).build();
  }
}
