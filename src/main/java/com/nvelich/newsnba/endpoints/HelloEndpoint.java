package com.nvelich.newsnba.endpoints;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jvnet.hk2.annotations.Service;
import com.nvelich.newsnba.Data;


@Service
@Path("/hello")
public class HelloEndpoint {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String hello() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Data data = new Data();
        data.age=10;
        data.name="nikita";
        String result = objectMapper.writeValueAsString(data);
        return result;
    }

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    public Response hello2() {
        return Response.ok().build();
    }
}

