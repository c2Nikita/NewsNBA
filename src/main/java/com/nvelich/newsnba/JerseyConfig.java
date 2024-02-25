package com.nvelich.newsnba;

import com.nvelich.newsnba.endpoints.FetchNewsByPlayerEndpoint;
import com.nvelich.newsnba.endpoints.PlayerRequestsCountEndpoint;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JerseyConfig extends ResourceConfig {
    public JerseyConfig() {
        register(FetchNewsByPlayerEndpoint.class);
        register(PlayerRequestsCountEndpoint.class);
    }
}