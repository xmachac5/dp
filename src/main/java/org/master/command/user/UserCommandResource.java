package org.master.command.user;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.master.command.user.commands.CreateUserCommand;
import org.master.command.user.commands.DeleteUserCommand;
import org.master.command.user.commands.UpdateUserCommand;
import org.master.command.user.handler.UserCommandHandler;

@Path("/users")
@Tag(name = "User Commands", description = "Handles commands related to user creation")
public class UserCommandResource {

    @Inject
    UserCommandHandler userCommandHandler;

    @POST
    @Operation(summary = "Create a new user", description = "Creates a new user in the system")
    public Response createUser(CreateUserCommand command) {
        // Handle the command by passing it to the UserCommandHandler
        userCommandHandler.handleCreateUserCommand(command.getName(), command.getEmail());
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateUser(UpdateUserCommand command) {
        userCommandHandler.handleUpdateUserCommand(command);
        return Response.status(Response.Status.OK).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteUser(@PathParam("id") Long id) {
        DeleteUserCommand command = new DeleteUserCommand();
        command.setId(id);
        userCommandHandler.handleDeleteUserCommand(command);
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
