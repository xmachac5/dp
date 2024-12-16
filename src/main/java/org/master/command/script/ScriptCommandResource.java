package org.master.command.script;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.master.command.script.commands.CreateScriptCommand;
import org.master.command.script.commands.DeleteScriptCommand;
import org.master.command.script.commands.PublishScriptCommand;
import org.master.command.script.commands.UpdateScriptCommand;
import org.master.command.script.handler.ScriptCommandHandler;
import org.master.dto.script.ScriptCreateDTO;
import org.master.dto.script.ScriptUpdateDTO;

import java.util.UUID;

@Path("/scripts")
@Tag(name = "Scripts Commands", description = "Handles commands related to scripts")
public class ScriptCommandResource {

    @Inject
    ScriptCommandHandler scriptCommandHandler;

    @Operation(summary = "Create a new script", description = "Creates a script with the provided data")
    @POST
    @RolesAllowed("admin")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createScript(@Valid ScriptCreateDTO scriptCreateDTO){
        UUID newScript = scriptCommandHandler.handle(new CreateScriptCommand(scriptCreateDTO.getVariables(), scriptCreateDTO.getName(),
                scriptCreateDTO.getCode()));
        return Response.status(Response.Status.CREATED)
                .entity(newScript)
                .build();
    }

    @Operation(summary = "Update script", description = "Update script with the provided data")
    @PUT
    @RolesAllowed("admin")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateScript(@QueryParam("script id") @DefaultValue("beb7e03a-be7f-441d-b562-865f8fdc3aa9") UUID id,
                                 @Valid ScriptUpdateDTO scriptUpdateDTO){
        try{
            scriptCommandHandler.handle(new UpdateScriptCommand(id, scriptUpdateDTO.getVariables(),
                    scriptUpdateDTO.getName(),scriptUpdateDTO.getCode()));
            return Response.status(Response.Status.OK).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (IllegalStateException e){
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @Operation(summary = "Delete script", description = "Delete script with the provided data")
    @DELETE
    @RolesAllowed("admin")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteScript(@QueryParam("script id") @DefaultValue("beb7e03a-be7f-441d-b562-865f8fdc3aa9") UUID id){
        try{
            scriptCommandHandler.handle(new DeleteScriptCommand(id));
            return Response.status(Response.Status.OK).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (IllegalStateException e){
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @Operation(summary = "Publish script", description = "Publish script with the provided data")
    @PUT
    @Path("/publish")
    @RolesAllowed("admin")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response publishScript(@QueryParam("script id") @DefaultValue("beb7e03a-be7f-441d-b562-865f8fdc3aa9") UUID id){
        try {
            scriptCommandHandler.handle(new PublishScriptCommand(id));
            return Response.status(Response.Status.OK).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (IllegalStateException e){
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
}
