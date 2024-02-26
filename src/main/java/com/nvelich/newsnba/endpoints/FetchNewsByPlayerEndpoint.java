package com.nvelich.newsnba.endpoints;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.nvelich.newsnba.rapidapi.RapidApiService;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jvnet.hk2.annotations.Service;
import com.nvelich.newsnba.models.Data;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;
import java.sql.DriverManager;


@Service
@Path("/articles")
public class FetchNewsByPlayerEndpoint {

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

