package com.kask.achievement.controller;

import com.kask.achievement.controller.dto.GetAchievementResponse;
import com.kask.achievement.controller.dto.GetAchievementsResponse;
import com.kask.achievement.entity.Achievement;
import com.kask.achievement.service.AchievementService;
import com.kask.user.entity.UserRole;
import lombok.NoArgsConstructor;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;

@RolesAllowed(UserRole.USER)
@Path("/achievements")
@NoArgsConstructor
public class AchievementController {

    private AchievementService achievementService;

    @EJB
    public void setAchievementService(AchievementService achievementService) {
        this.achievementService = achievementService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAchievements(){
        return Response.ok(GetAchievementsResponse.entityToDtoMapper().apply(achievementService.getAllAchievements())).build();
    }

    @GET
    @Path("/{achievementId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAchievement(@PathParam("achievementId") Integer achievementId){
        Optional<Achievement> achievement = achievementService.getAchievement(achievementId);
        if(achievement.isPresent()){
            return Response.ok(GetAchievementResponse.entityToDtoMapper().apply(achievement.get())).build();
        } else{
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @DELETE
    @Path("/{achievementId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteAchievement(@PathParam("achievementId") Integer achievementId){
        Optional<Achievement> achievement = achievementService.getAchievement(achievementId);
        if(achievement.isPresent()){
            achievementService.deleteAchievement(achievement.get());
            return Response.status(Response.Status.NO_CONTENT).build();
        } else{
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
