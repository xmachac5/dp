package org.master.command.user;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.master.command.user.commands.CreateUserCommand;
import org.master.command.user.commands.DeleteUserCommand;
import org.master.command.user.commands.UpdatePasswordCommand;
import org.master.command.user.commands.UpdateUserCommand;
import org.master.command.user.handler.UserCommandHandler;
import org.master.dto.user.CreateUserDTO;
import org.master.dto.user.UpdatePasswordDTO;
import org.master.dto.user.UpdateUserDTO;

import java.util.UUID;

@Path("/users")
@Tag(name = "User Commands", description = "Handles commands related to user creation")
public class UserCommandResource {

    @Inject
    UserCommandHandler userCommandHandler;

    @POST
    @RolesAllowed("admin")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createUser(@Valid CreateUserDTO createUserDTO){
        userCommandHandler.handleCreateUserCommand(new CreateUserCommand(createUserDTO.getName(), createUserDTO.getEmail(),
                createUserDTO.getLogin(), createUserDTO.getPassword(), createUserDTO.getRole()));
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @RolesAllowed("admin")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateUser(@Valid UpdateUserDTO updateUserDTO) {
        try {
            userCommandHandler.handleUpdateUserCommand(new UpdateUserCommand(updateUserDTO.getUuid(), updateUserDTO.getName(),
                    updateUserDTO.getEmail()));
            return Response.status(Response.Status.OK).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("/change-password")
    @RolesAllowed("admin")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response changePassword(UpdatePasswordDTO updatePasswordDTO) {
        try {
            userCommandHandler.handleUpdatePasswordCommand( new UpdatePasswordCommand(updatePasswordDTO.getUuid(),
                    updatePasswordDTO.getNewPassword()));
            return Response.ok("Password updated successfully").build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{uuid}")
    @RolesAllowed("admin")
    public Response deleteUser(@PathParam("uuid") UUID uuid) {
        try {
            userCommandHandler.handleDeleteUserCommand(new DeleteUserCommand(uuid));
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }
}
