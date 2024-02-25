package com.nvelich.newsnba.services.rapidApi;

import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

@Component
public class RapidApiHTTPClient {
    public CompletableFuture<HttpResponse<String>> fetchNewsBy(String player) {
        String fullEndpoint = RapidApiConfig.URL + "/articles?player=" + player;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(fullEndpoint))
                .header("X-RapidAPI-Key", RapidApiConfig.KEY)
                .header("X-RapidAPI-Host", RapidApiConfig.HOST)
                .build();

        return HttpClient.newHttpClient()
                .sendAsync(request, HttpResponse.BodyHandlers.ofString());
    }
}
