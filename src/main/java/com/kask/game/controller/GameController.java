package com.kask.game.controller;

import com.kask.game.dto.CreateGameRequest;
import com.kask.game.dto.GetGameResponse;
import com.kask.game.dto.GetGamesResponse;
import com.kask.game.dto.UpdateGameRequest;
import com.kask.game.entity.Game;
import com.kask.game.service.GameService;
import com.kask.user.entity.User;
import com.kask.user.entity.UserRole;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;

@RolesAllowed(UserRole.USER)
@Path("/games")
public class GameController {

    private GameService gameService;

    public GameController(){}

    @EJB
    public void setGameService(GameService gameService){
        this.gameService = gameService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getGames() {
        return Response
                .ok(GetGamesResponse.entityToDtoMapper().apply(gameService.getAllGames()))
                .build();
    }

    @GET
    @Path("{gameName}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getGame(@PathParam("gameName") String gameName){
        Optional<Game> game = gameService.getGame(gameName);
        if (game.isPresent()){
            return Response
                    .ok(GetGameResponse.entityToDtoMapper().apply(game.get()))
                    .build();
        } else {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
        }
    }

    @POST
    @RolesAllowed(UserRole.ADMIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postGame(CreateGameRequest request){
        Game game = CreateGameRequest.dtoToEntityMapper().apply(request);
        try {
            gameService.createGame(game);
            return Response.status(Response.Status.CREATED).build();
        } catch (IllegalArgumentException e){
            return Response.status(Response.Status.BAD_REQUEST).entity("Game with that name exists").build();
        }
    }

    @PUT
    @Path("{gameName}")
    @RolesAllowed(UserRole.ADMIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateGame(@PathParam("gameName") String gameName ,UpdateGameRequest request){
        Optional<Game> game = gameService.getGame(gameName);
        if (game.isPresent()){
            UpdateGameRequest.dtoToEntityMapper().apply(game.get(), request);
            gameService.updateGame(game.get());
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @DELETE
    @Path("{gameName}")
    @RolesAllowed(UserRole.ADMIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteGame(@PathParam("gameName") String gameName){
        Optional<Game> game = gameService.getGame(gameName);
        if (game.isPresent()){
            gameService.deleteGame(gameName);
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("Game with that name exists").build();
        }
    }

}