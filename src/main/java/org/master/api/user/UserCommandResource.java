package org.master.api.user;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.master.command.user.commands.DeleteUserCommand;
import org.master.command.user.commands.UpdatePasswordCommand;
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
        userCommandHandler.handleCreateUserCommand(createUserDTO);
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @RolesAllowed("admin")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateUser(@Valid UpdateUserDTO updateUserDTO) {
        userCommandHandler.handleUpdateUserCommand(updateUserDTO);
        return Response.status(Response.Status.OK).build();
    }

    @POST
    @Path("/change-password")
    @RolesAllowed("admin")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response changePassword(UpdatePasswordDTO updatePasswordDTO) {
        try {
            UpdatePasswordCommand command = new UpdatePasswordCommand(updatePasswordDTO.getUuid(), updatePasswordDTO.getNewPassword());
            userCommandHandler.handleUpdatePasswordCommand(command);
            return Response.ok("Password updated successfully").build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{uuid}")
    @RolesAllowed("admin")
    public Response deleteUser(@PathParam("uuid") UUID uuid) {
        DeleteUserCommand command = new DeleteUserCommand();
        command.setUuid(uuid);
        userCommandHandler.handleDeleteUserCommand(command);
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
