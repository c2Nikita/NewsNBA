package com.nvelich.newsnba.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.nvelich.newsnba.models.Player;
import com.nvelich.newsnba.models.News;
import com.nvelich.newsnba.rapidapi.RapidApiService;
import com.nvelich.newsnba.service.NewsService;
import com.nvelich.newsnba.service.PlayerService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import com.nvelich.newsnba.models.JsonEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;


@RestController
@Path("/articles")
public class FetchNewsByPlayerController {

    @Autowired
    private NewsService newsService;
    @Autowired
    private PlayerService playerService;
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response articles(@QueryParam("player") String player) throws IOException, InterruptedException {
        if (player != null && Pattern.matches("[a-zA-Z]+", player)) {
            RapidApiService service = new RapidApiService();
            List<JsonEntity> list = service.fetchNewsBy(player);
            ObjectMapper mapper = new ObjectMapper();
            String jsonResult = mapper.writeValueAsString(list);
            Player hooper = new Player();
            List<Player> array = playerService.getAllPlayers();
            Player savedPlayer;
            if(isNameInList(array,player)) {
                Player updatedPlayer = giveInList(array,player);
                Player existingPlayer = playerService.getPlayerById(updatedPlayer.getId());
                existingPlayer.setName(updatedPlayer.getName());
                existingPlayer.setCount(updatedPlayer.getCount()+1);
                playerService.savePlayer(existingPlayer);
                savedPlayer=updatedPlayer;

            }
            else {
                hooper.setName(player);
                hooper.setCount(1);
                savedPlayer = playerService.savePlayer(hooper);
            }


            int check = 1;
            News news = new News();
            List<News> newsFromDataBase = newsService.getAllNews();
            for(JsonEntity entity : list) {
                news.setText(entity.getSource());
                news.setTitle(entity.getTitle());
                news.setPlayer(savedPlayer);
                for(News newsEntity :newsFromDataBase)
                {
                    if(news.getTitle().equals(newsEntity.getTitle())) {
                        check = 0;

                    }
                }
                news.setId(null);
                if(check == 1) {

                    newsService.saveNews(news);
                }
                check = 1;

            }



            return Response.ok(jsonResult).build(); //
        } else  {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(" Wrong name")
                    .build();
        }
    }
    public boolean isNameInList(List<Player> players, String nameToCheck) {
        for (Player player : players) {
            if (player.getName().equals(nameToCheck)) {
                return true;
            }
        }
        return false;
    }
    public Player giveInList(List<Player> players, String nameToCheck) {
        Player answer=new Player();
        for (Player player : players) {
            if (player.getName().equals(nameToCheck)) {
                answer.setId(player.getId());
                answer.setName(player.getName());
                answer.setId(player.getId());
                answer.setCount(player.getCount());
            }
        }
        return answer;
    }

}

