package com.nvelich.newsnba.controller;

import com.nvelich.newsnba.cache.PlayerCache;
import com.nvelich.newsnba.exceptions.ErrorResponse;
import com.nvelich.newsnba.exceptions.YourFriendly400Exception;
import com.nvelich.newsnba.exceptions.YourFriendly404Exception;
import com.nvelich.newsnba.models.News;
import com.nvelich.newsnba.models.Player;
import com.nvelich.newsnba.repositories.PlayerRepository;
import com.nvelich.newsnba.service.PlayerService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.ws.rs.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.regex.Pattern;

@Slf4j
@Component
@Path("/players")
@Produces(MediaType.APPLICATION_JSON)
public class PlayerController {

    @Autowired
    private PlayerService playerService;
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private PlayerCache playerCache;

    @POST
    public Response addPlayer(Player player) {
        Player savedPlayer = playerService.savePlayer(player);
        return Response.ok(savedPlayer).build();
    }

    @GET
    public Response getAllPlayers() {
        List<Player> players = playerService.getAllPlayers();
        return Response.ok(players).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPlayerById(@PathParam("id") Long id) {
        Player player = playerService.getPlayerById(id);
        return Response.ok(player).build();

    }

    @DELETE
    @Path("/{id}")
    public Response deletePlayerById(@PathParam("id") Long id) {
        playerService.deletePlayerById(id);
        return Response.ok().build();
    }

    @PUT
    @Path("/{id}")
    public Response updatePlayer(@PathParam("id") Long id, Player updatedPlayer) {
        Player existingPlayer = playerService.getPlayerById(id);
        existingPlayer.setName(updatedPlayer.getName());
        existingPlayer.setCount(updatedPlayer.getCount());
        Player savedPlayer = playerService.savePlayer(existingPlayer);
        return Response.ok(existingPlayer).build();

    }

    @GET
    @Path("byName/{name}")
    public Response getPlayerByName(@PathParam("name") String playerName) {
        if (Pattern.matches("[a-zA-Z]+", playerName)) {
            Player player = playerCache.getPlayer(playerName);
            if (player == null) {
                player = playerRepository.findByName(playerName);
            }
            return Response.ok(player).build();
        } else {
            log.info("Некорентный ввод данных");
            throw new YourFriendly400Exception("Вы некоректно ввели данные!", 400);
        }
    }
}