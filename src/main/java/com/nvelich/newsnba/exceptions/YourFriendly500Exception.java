package com.nvelich.newsnba.exceptions;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

public class YourFriendly500Exception extends WebApplicationException {
    public YourFriendly500Exception(String message, int code) {
        super(Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(new ErrorResponse(message, code))
                .type(MediaType.APPLICATION_JSON)
                .build());
    }
}