package com.nvelich.newsnba.exceptions;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

public class YourFriendly404Exception extends WebApplicationException {
    public YourFriendly404Exception(String message, int code) {
        super(Response.status(Response.Status.NOT_FOUND)
                .entity(new ErrorResponse(message, code))
                .type(MediaType.APPLICATION_JSON)
                .build());
    }
}