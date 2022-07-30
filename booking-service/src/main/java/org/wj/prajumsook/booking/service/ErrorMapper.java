package org.wj.prajumsook.booking.service;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Provider
public class ErrorMapper implements ExceptionMapper<Exception> {

  @Override
  public Response toResponse(Exception ex) {
    int statusCode = Response.Status.BAD_REQUEST.getStatusCode();
    if (ex instanceof WebApplicationException) {
      statusCode = ((WebApplicationException) ex).getResponse().getStatus();
    }

    ObjectMapper mapper = new ObjectMapper();
    ObjectNode error = mapper.createObjectNode();
    error.put("exceptionType", ex.getClass().getName());
    error.put("statusCode", statusCode);
    error.put("error", (ex.getMessage() != null) ? ex.getMessage() : "unknown error");

    return Response.status(statusCode).entity(error).build();
  }

}
