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
import org.wj.prajumsook.booking.model.BoardingPass;
import org.wj.prajumsook.booking.service.BoardingPassService;

@Path("/boarding_passes/v1")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BoardingPassResource {

  @Inject
  BoardingPassService boardingPassService;

  @GET
  public Set<BoardingPass> findAll() {
    return boardingPassService.findAll();
  }

  @GET
  @Path("/{id}")
  public BoardingPass findById(@RestPath Long id) {
    return boardingPassService.findById(id);
  }

  @POST
  public BoardingPass create(BoardingPass boardingPass) {
    return boardingPassService.create(boardingPass);
  }

  @DELETE
  @Path("/{id}")
  public BoardingPass delete(@RestPath Long id) {
    return boardingPassService.delete(id);
  }
}
