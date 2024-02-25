package com.nvelich.newsnba.endpoints;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jvnet.hk2.annotations.Service;
import com.nvelich.newsnba.models.Data;


@Service
@Path("/articles")
public class FetchNewsByPlayerEndpoint {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response articles(@QueryParam("player") String player) throws JsonProcessingException {
        if (player != null) {
            return Response.ok("nikola").build(); // TODO: make request to rapidapi and save to database
        } else {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Player parametre is required")
                    .build();
        }

    }
}

