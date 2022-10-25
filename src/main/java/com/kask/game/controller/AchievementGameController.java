package com.kask.game.controller;

import com.kask.achievement.controller.dto.CreateAchievementRequest;
import com.kask.achievement.controller.dto.UpdateAchievementRequest;
import com.kask.achievement.entity.Achievement;
import com.kask.achievement.service.AchievementService;
import com.kask.game.entity.Game;
import com.kask.game.service.GameService;
import com.kask.user.entity.UserRole;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.security.InvalidParameterException;
import java.util.NoSuchElementException;
import java.util.Optional;

@RolesAllowed(UserRole.USER)
@Path("/games/{gameName}/achievements")
public class AchievementGameController {

    private GameService gameService;
    private AchievementService achievementService;

    public AchievementGameController(){}

    @EJB
    public void setGameService(GameService gameService){
        this.gameService = gameService;
    }

    @EJB
    public void setAchievementService(AchievementService achievementService){
        this.achievementService = achievementService;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createAchievement(@PathParam("gameName") String pathGameName, CreateAchievementRequest request) {
        try {
            Achievement achievement = CreateAchievementRequest.dtoToEntityMapper(gameName -> gameService.getGame(gameName).orElseThrow(), pathGameName).apply(request);
            achievementService.createAchievement(achievement);
            return Response.status(Response.Status.CREATED).build();
        } catch(NoSuchElementException e){
            return Response.status(Response.Status.BAD_REQUEST).entity("Provided game does not exist").build();
        } catch(InvalidParameterException e){
            return Response.status(Response.Status.BAD_REQUEST).entity("Achievement with provided name already exist").build();
        }
    }

    @PUT
    @Path("/{achievementId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateAchievement(@PathParam("gameName") String gameName, @PathParam("achievementId") Integer achievementId ,UpdateAchievementRequest request){
        Optional<Game> game = gameService.getGame(gameName);
        if (game.isEmpty()){
            return Response.status(Response.Status.BAD_REQUEST).entity("Provided game does not exist").build();
        }
        Optional<Achievement> achievement = achievementService.getAchievement(achievementId);
        if (achievement.isEmpty()){
            return Response.status(Response.Status.BAD_REQUEST).entity("Provided achievement does not exist").build();
        }

        UpdateAchievementRequest.dtoToEntityMapper().apply(achievement.get(), request);
        achievementService.updateAchievement(achievement.get());
        return Response.noContent().build();
    }
}
