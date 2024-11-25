package org.master.api;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.master.command.screen.commands.CreateScreenCommand;
import org.master.command.screen.handler.ScreenCommandHandler;
import org.master.dto.screen.ScreenCreateDTO;

@Path("/screens")
@Tag(name = "Screens Commands", description = "Handles commands related to screen creation")
public class ScreenCommandResource {

    @Inject
    ScreenCommandHandler screenCommandHandler;

    @Operation(summary = "Create a new screen", description = "Creates a screen with the provided data")
    @POST
    @RolesAllowed("admin")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createScreen(@Valid ScreenCreateDTO screenCreateDTO){
        screenCommandHandler.handle(new CreateScreenCommand(screenCreateDTO));
        return Response.status(Response.Status.CREATED).build();
    }
}
