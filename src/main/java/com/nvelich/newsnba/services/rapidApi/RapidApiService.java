package com.nvelich.newsnba.services.rapidApi;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nvelich.newsnba.services.database.PlayerDatabaseService;
import com.nvelich.newsnba.services.rapidApi.models.NewsItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
public class RapidApiService {
    private final ObjectMapper objectMapper;
    private final RapidApiHTTPClient rapidApiHTTPClient;
    private final PlayerDatabaseService playerDatabaseService;

    @Autowired
    public RapidApiService(ObjectMapper objectMapper,
                           RapidApiHTTPClient rapidApiHTTPClient,
                           PlayerDatabaseService playerDatabaseService) {
        this.objectMapper = objectMapper;
        this.rapidApiHTTPClient = rapidApiHTTPClient;
        this.playerDatabaseService = playerDatabaseService;
    }

    public CompletableFuture<List<NewsItem>> fetchNewsBy(String player) {
        return rapidApiHTTPClient
                .fetchNewsBy(player)
                .thenApply(response ->
                        handleResponse(response, player)
                );
    }

    private List<NewsItem> handleResponse(HttpResponse<String> response, String player) {
        if (response.statusCode() >= 200 && response.statusCode() <= 204) {
            playerDatabaseService.updateSearchQueriesCountForPlayer(player);
            return mapJSONToObject(response);
        } else {
            return Collections.emptyList();
        }
    }

    private List<NewsItem> mapJSONToObject(HttpResponse<String> response) {
        try {
            return objectMapper.readValue(
                    response.body(),
                    new TypeReference<List<NewsItem>>() {}
            );
        } catch (IOException e) {
            return Collections.emptyList();
        }
    }
}
