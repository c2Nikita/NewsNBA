package com.nvelich.newsnba.models;

import com.fasterxml.jackson.annotation.JsonProperty;



public class Data {
    @JsonProperty("title")
    private String title;
    @JsonProperty("url")
    private String url;
    @JsonProperty("source")
    private String source;
}

