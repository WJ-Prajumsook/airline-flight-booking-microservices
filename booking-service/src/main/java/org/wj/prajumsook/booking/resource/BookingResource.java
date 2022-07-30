package org.wj.prajumsook.booking.resource;

import java.util.Set;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.reactive.RestPath;
import org.wj.prajumsook.booking.model.Booking;
import org.wj.prajumsook.booking.service.BookingService;

@Path("/bookings/v1")
public class BookingResource {

  @Inject
  BookingService bookingService;

  @GET
  public Set<Booking> findAll() {
    return bookingService.findAll();
  }

  @GET
  @Path("/{id}")
  public Booking findById(@RestPath Long id) {
    return bookingService.findById(id);
  }

  @POST
  public Booking create(Booking booking) {
    return bookingService.create(booking);
  }

  @DELETE
  @Path("/{id}")
  public Booking delete(@RestPath Long id) {
    return bookingService.delete(id);
  }

}
