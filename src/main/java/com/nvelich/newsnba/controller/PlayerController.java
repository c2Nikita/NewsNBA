package com.nvelich.newsnba.controller;

import com.nvelich.newsnba.cache.PlayerCache;
import com.nvelich.newsnba.models.News;
import com.nvelich.newsnba.models.Player;
import com.nvelich.newsnba.repositories.PlayerRepository;
import com.nvelich.newsnba.service.PlayerService;
import jakarta.ws.rs.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

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
    public Response getPlayerById(@PathParam("id") Long id) {
        Player player = playerService.getPlayerById(id);
        if (player != null) {
            return Response.ok(player).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
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
        if (existingPlayer == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Player with id " + id + " not found").build();
        }


        existingPlayer.setName(updatedPlayer.getName());
        existingPlayer.setCount(updatedPlayer.getCount());


        Player savedPlayer = playerService.savePlayer(existingPlayer);
        return Response.ok(savedPlayer).build();
    }

    @GET
    @Path("byName/{name}")
    public Response getPlayerByName(@PathParam("name") String playerName) {
        Player player = playerCache.getPlayer(playerName);
        if (player == null) {
            player = playerRepository.findByName(playerName);
            if (player != null) {
                playerCache.addPlayer(playerName, player);
            }
        }
        if (player != null) {
            return Response.ok(player).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

    }
}