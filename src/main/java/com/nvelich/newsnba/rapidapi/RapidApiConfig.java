package com.nvelich.newsnba.rapidapi;

public class RapidApiConfig {

    private RapidApiConfig() {
        throw new IllegalStateException("Utility class");
    }

    static final String URL = "https://nba-latest-news.p.rapidapi.com";
    static final String KEY = "Your rapidapi key";
    static final String HOST = "nba-latest-news.p.rapidapi.com";
}
