package com.nvelich.newsnba.rapidapi;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nvelich.newsnba.models.Data;
import org.jvnet.hk2.annotations.Service;


import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
@Service
public class RapidApiService {
    public List<Data> fetchNewsBy(String player) throws IOException, InterruptedException {
        String fullEndpoint = RapidApiConfig.URL + "/articles?player=" + player;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(fullEndpoint))
                .header("X-RapidAPI-Key", RapidApiConfig.KEY)
                .header("X-RapidAPI-Host", RapidApiConfig.HOST)
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());

        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.readValue(
                response.body(),
                new TypeReference<List<Data>>() {}
        );
    }
}
