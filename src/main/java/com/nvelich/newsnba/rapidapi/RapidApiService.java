package com.nvelich.newsnba.rapidapi;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class RapidApiService {
    public String fetchNewsBy(String player) throws IOException, InterruptedException {
        String fullEndpoint = RapidApiConfig.url + "/articles?player=" + player;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(fullEndpoint))
                .header("X-RapidAPI-Key", RapidApiConfig.key)
                .header("X-RapidAPI-Host", RapidApiConfig.host)
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());

    }
}
