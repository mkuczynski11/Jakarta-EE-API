package com.kask.user.controller;

import com.kask.servlet.MimeTypes;
import com.kask.user.dto.*;
import com.kask.user.entity.User;
import com.kask.user.entity.UserRole;
import com.kask.user.service.UserService;
import lombok.NoArgsConstructor;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.util.Optional;

@NoArgsConstructor
@Path("/users")
@RolesAllowed(UserRole.USER)
public class UserController {
    private UserService userService;

    @EJB
    public void setUserService(UserService userService){
        this.userService = userService;
    }

    @GET
    @RolesAllowed(UserRole.ADMIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsers() {
        return Response.ok(GetUsersResponse.entityToDtoMapper().apply(userService.getAllUsers())).build();
    }

    @GET
    @Path("{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@PathParam("username") String username) {
        Optional<User> user = userService.getUser(username);
        if (user.isPresent()) {
            return Response.ok(GetUserResponse.entityToDtoMapper().apply(user.get())).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("{username}/avatar")
    @Produces(MimeTypes.IMAGE_PNG)
    public Response getUserAvatar(@PathParam("username") String username) {
        Optional<User> user = userService.getUser(username);
        if (user.isPresent() && user.get().getAvatar() != null) {
            return Response.ok(user.get().getAvatar()).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @PermitAll
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createUser(CreateUserRequest createUserRequest) {
        User user = CreateUserRequest.dtoToEntityMapper().apply(createUserRequest);
        userService.createUser(user);
        return Response
                .created(UriBuilder
                        .fromResource(UserController.class)
                        .path("{username}")
                        .build(user.getUsername())).build();
    }

    @PUT
    @Path("{username}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateUser(@PathParam("username") String username, UpdateUserRequest updateUserRequest) {
        Optional<User> user = userService.getUser(username);

        if (user.isPresent()){
            UpdateUserRequest.dtoToEntityMapper().apply(user.get(), updateUserRequest);
            userService.updateUser(user.get());
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @PUT
    @Path("{username}/password")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateUserPassword(@PathParam("username") String username, UpdateUserPasswordRequest updateUserPasswordRequest) {
        Optional<User> user = userService.getUser(username);

        if (user.isPresent()) {
            UpdateUserPasswordRequest.dtoToEntityMapper().apply(user.get(), updateUserPasswordRequest);
            userService.updateUser(user.get());
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @PUT
    @RolesAllowed(UserRole.ADMIN)
    @Path("{username}/roles")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateUserRole(@PathParam("username") String username, UpdateUserRolesRequest updateUserRoleRequest) {
        Optional<User> user = userService.getUser(username);

        if (user.isPresent()) {
            UpdateUserRolesRequest.dtoToEntityMapper().apply(user.get(), updateUserRoleRequest);
            userService.updateUser(user.get());
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @DELETE
    @RolesAllowed(UserRole.ADMIN)
    @Path("{username}")
    public Response deleteUser(@PathParam("username") String username) {
        Optional<User> user = userService.getUser(username);

        if (user.isPresent()) {
            userService.deleteUser(user.get());
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
