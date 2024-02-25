package com.nvelich.newsnba.endpoints;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.nvelich.newsnba.services.database.PlayerDatabaseService;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;
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

        try {
            String JSON = mapToJSON(player, count);
            return Response.ok(JSON).build();
        } catch (JsonProcessingException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error parsing objects to response JSON")
                    .build();
        }
    }

    private String mapToJSON(String player, Integer count) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("player", player);
        jsonMap.put("queries_count", count);
        return objectMapper.writeValueAsString(jsonMap);
    }
}
