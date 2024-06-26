package com.nvelich.newsnba.controller;

import com.nvelich.newsnba.exceptions.YourFriendly400Exception;
import com.nvelich.newsnba.exceptions.YourFriendly404Exception;
import com.nvelich.newsnba.exceptions.YourFriendly500Exception;
import com.nvelich.newsnba.models.News;
import com.nvelich.newsnba.service.NewsService;
import jakarta.ws.rs.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
import java.util.regex.Pattern;

@Slf4j
@Component
@Path("/news")
@Produces(MediaType.APPLICATION_JSON)
public class NewsController {

    @Autowired
    private NewsService newsService;

    @POST
    public Response addNews(News news) {
        News savedNews = newsService.saveNews(news);
        return Response.ok(savedNews).build();
    }

    @GET
    public Response getAllNews() {
        List<News> newsList = newsService.getAllNews();
        return Response.ok(newsList).build();
    }

    @GET
    @Path("/{id}")
    public Response getNewsById(@PathParam("id") Long id) {
        News news = newsService.getNewsById(id);
        return Response.ok(news).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteNewsById(@PathParam("id") Long id) {
        newsService.deleteNewsById(id);
        return Response.ok().build();
    }

    @PUT
    @Path("/{id}")
    public Response updateNews(@PathParam("id") Long id, News updatedNews) {
        News existingNews = newsService.getNewsById(id);
        existingNews.setTitle(updatedNews.getTitle());
        existingNews.setText(updatedNews.getText());
        News savedNews = newsService.saveNews(existingNews);
        return Response.ok(savedNews).build();
    }
}