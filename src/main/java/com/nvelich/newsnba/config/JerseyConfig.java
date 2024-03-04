package com.nvelich.newsnba.config;

import com.nvelich.newsnba.controller.FetchNewsByPlayerController;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        register(FetchNewsByPlayerController.class);
    }
}