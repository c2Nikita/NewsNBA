package com.nvelich.newsnba.config;

import com.nvelich.newsnba.controller.FetchNewsByPlayerController;
import com.nvelich.newsnba.controller.NewsController;
import com.nvelich.newsnba.controller.PlayerController;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        register(FetchNewsByPlayerController.class);
        register(PlayerController.class);
        register(NewsController.class);
    }
}
