package com.nvelich.newsnba;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy
@SpringBootApplication
public class NewsNbaApplication {

    public static void main(String[] args) {
        SpringApplication.run(NewsNbaApplication.class, args);
    }

}
