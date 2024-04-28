package com.nvelich.newsnba.exceptions;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

public class YourFriendly400Exception extends WebApplicationException {
    public YourFriendly400Exception(String message, int code) {
        super(Response.status(Response.Status.BAD_REQUEST)
                .entity(new ErrorResponse(message, code))
                .type(MediaType.APPLICATION_JSON)
                .build());
    }
}