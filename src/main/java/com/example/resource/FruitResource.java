package com.example.resource;

import com.example.model.Fruit;
import com.example.model.FruitDto;
import com.example.model.FruityVice;
import com.example.repo.FruitResourceRepository;
import com.example.service.FruityViceService;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("/fruit")
public class FruitResource {

    @RestClient
    FruityViceService fruityViceService;

    private final FruitResourceRepository fruitResourceRepository;

    public FruitResource(FruitResourceRepository fruitResourceRepository) {
        this.fruitResourceRepository = fruitResourceRepository;
    }

    @GET
    @Path("/names/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response fruityVice(@PathParam("name") String name) {
        FruityVice fruityVice = fruityViceService.getFruitByName(name);
        List<Fruit> fruits = fruitResourceRepository.findByName(name);
        if(fruits.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        List<FruitDto> result = fruits.stream().map(f -> FruitDto.of(f, fruityVice)).collect(Collectors.toList());
        if(result.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(result).
                build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response fruits(){
        List<Fruit> fruits = fruitResourceRepository.listAll();
        if(fruits.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        List<FruitDto> result = fruits.stream().map(f -> {
            FruityVice fruityVice = fruityViceService.getFruitByName(f.name);
            return FruitDto.of(f, fruityVice);
        }).collect(Collectors.toList());
        if(result.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(result).build();
    }

    @GET
    @Path("/season/{season}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response fruits(@PathParam("season") String season) {
        List<Fruit> fruits = fruitResourceRepository.findBySeason(season);
        if(fruits.isEmpty()) {
            fruits = fruitResourceRepository.listAll();
        }
        if(fruits.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        List<FruitDto> result = fruits.stream().map(f -> {
            FruityVice fruityVice = fruityViceService.getFruitByName(f.name);
            return FruitDto.of(f, fruityVice);
        }).collect(Collectors.toList());
        if(result.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
       return Response.ok(result).
               build();
    }

    @Transactional
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addFruit(Fruit fruit) {
        fruitResourceRepository.persistAndFlush(fruit);
        return Response.ok().build();
    }
}
