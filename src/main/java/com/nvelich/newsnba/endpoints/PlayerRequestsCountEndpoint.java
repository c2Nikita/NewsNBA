package com.nvelich.newsnba.endpoints;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nvelich.newsnba.services.database.PlayerDatabaseService;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.regex.Pattern;

@Service
@Path("/player/search/count")
public class PlayerRequestsCountEndpoint {
    private final PlayerDatabaseService playerDatabaseService;

    @Autowired
    public PlayerRequestsCountEndpoint(PlayerDatabaseService playerDatabaseService) {
        this.playerDatabaseService = playerDatabaseService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response articles(@QueryParam("player") String player) throws JsonProcessingException {
        if (player == null || !Pattern.matches("[a-zA-Z]+", player)) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Player param is missing or wrong")
                    .build();
        }

        Integer count = playerDatabaseService.getSearchQueriesCountForPlayer(player);
        return Response.ok(count).build();
    }
}
