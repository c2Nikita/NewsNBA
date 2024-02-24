package com.nvelich.newsnba.config;

import com.nvelich.newsnba.endpoints.HelloEndpoint;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        register(HelloEndpoint.class);
    }
}