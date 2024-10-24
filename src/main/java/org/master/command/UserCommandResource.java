package org.master.command;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.master.command.handler.UserCommandHandler;
import org.master.command.model.CreateUserCommand;

@Path("/users")
@Tag(name = "User Commands", description = "Handles commands related to user creation")
@Consumes(MediaType.APPLICATION_JSON)
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
}
