package com.nvelich.newsnba.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.nvelich.newsnba.rapidapi.RapidApiService;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import com.nvelich.newsnba.models.Data;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;


@RestController
@Path("/articles")
public class FetchNewsByPlayerController {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response articles(@QueryParam("player") String player) throws IOException, InterruptedException {
        if (player != null && Pattern.matches("[a-zA-Z]+", player)) {
            RapidApiService service = new RapidApiService();
            List<Data> list = service.fetchNewsBy(player);
            ObjectMapper mapper = new ObjectMapper();
            String jsonResult = mapper.writeValueAsString(list);

            return Response.ok(jsonResult).build(); //
        } else  {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(" Wrong name")
                    .build();
        }
    }
}

