package org.master.command.screen;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.master.command.screen.commands.CreateScreenCommand;
import org.master.command.screen.commands.DeleteScreenCommand;
import org.master.command.screen.commands.PublishScreenCommands;
import org.master.command.screen.commands.UpdateScreenCommand;
import org.master.command.screen.handler.ScreenCommandHandler;
import org.master.dto.screen.ScreenCreateDTO;
import org.master.dto.screen.ScreenUpdateDTO;

import java.util.UUID;

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
        UUID newScreen = screenCommandHandler.handle(new CreateScreenCommand(screenCreateDTO.getData(), screenCreateDTO.getName(),
                screenCreateDTO.getColumns(), screenCreateDTO.getRowHeights(), screenCreateDTO.getPrimaryLanguageId(),
                screenCreateDTO.getUrl(), screenCreateDTO.getRowMaxHeights(), screenCreateDTO.getLocals(), screenCreateDTO.getVariableInit(),
                screenCreateDTO.getVariableInitMapping(), screenCreateDTO.getBackground(), screenCreateDTO.getTitle()));
        return Response.status(Response.Status.CREATED)
                .entity(newScreen)
                .build();
    }

    @Operation(summary = "Update new screen", description = "Update screen with the provided data")
    @PUT
    @RolesAllowed("admin")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateScreen(@QueryParam("screen id") @DefaultValue("beb7e03a-be7f-441d-b562-865f8fdc3aa9") UUID id,
                                 @Valid ScreenUpdateDTO screenUpdateDTO){
        try {
            screenCommandHandler.handle(new UpdateScreenCommand(id, screenUpdateDTO.getData(), screenUpdateDTO.getName(),
                    screenUpdateDTO.getColumns(), screenUpdateDTO.getRowHeights(), screenUpdateDTO.getPrimaryLanguageId(),
                    screenUpdateDTO.getUrl(), screenUpdateDTO.getRowMaxHeights(), screenUpdateDTO.getLocals(), screenUpdateDTO.getVariableInit(),
                    screenUpdateDTO.getVariableInitMapping(), screenUpdateDTO.getBackground(), screenUpdateDTO.getTitle()));
            return Response.status(Response.Status.OK).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (IllegalStateException e){
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @Operation(summary = "Delete screen", description = "Delete screen with the provided data")
    @DELETE
    @RolesAllowed("admin")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteScreen(@QueryParam("screen id") @DefaultValue("beb7e03a-be7f-441d-b562-865f8fdc3aa9") UUID id){
        try {
            screenCommandHandler.handle(new DeleteScreenCommand(id));
            return Response.status(Response.Status.OK).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (IllegalStateException e){
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @Operation(summary = "Publish screen", description = "Publish screen with the provided data")
    @PUT
    @Path("/publish")
    @RolesAllowed("admin")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response publishScreen(@QueryParam("screen id") @DefaultValue("beb7e03a-be7f-441d-b562-865f8fdc3aa9") UUID id){
        try {
            screenCommandHandler.handle(new PublishScreenCommands(id));
            return Response.status(Response.Status.OK).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (IllegalStateException e){
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
}
