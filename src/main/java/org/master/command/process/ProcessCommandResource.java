package org.master.command.process;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.master.command.process.commands.*;
import org.master.command.process.handler.ProcessCommandHandler;
import org.master.dto.process.ProcessCreateDTO;
import org.master.dto.process.ProcessUpdateDTO;

import java.util.UUID;

@Path("/process")
@Tag(name = "Process Commands", description = "Handles commands related to process")
public class ProcessCommandResource {
    @Inject
    ProcessCommandHandler processCommandHandler;

    @Operation(summary = "Create a new process", description = "Creates a process with the provided data")
    @POST
    @RolesAllowed("admin")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createProcess(@Valid ProcessCreateDTO processCreateDTO){
        processCommandHandler.handle(new CreateProcessCommand(
                processCreateDTO.getName(),
                processCreateDTO.getVariables()));
        return Response.status(Response.Status.CREATED).build();
    }

    @Operation(summary = "Update process", description = "Update process with the provided data")
    @PUT
    @RolesAllowed("admin")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateProcess(@QueryParam("process id") @DefaultValue("beb7e03a-be7f-441d-b562-865f8fdc3aa9") UUID id,
                                 @Valid ProcessUpdateDTO processUpdateDTO){
        try{
            processCommandHandler.handle(new UpdateProcessCommand(
                    id,
                    processUpdateDTO.getName(),
                    processUpdateDTO.getVariables()));
            return Response.status(Response.Status.OK).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (IllegalStateException e){
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @Operation(summary = "Delete process", description = "Delete process with the provided id")
    @DELETE
    @RolesAllowed("admin")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteProcess(@QueryParam("process id") @DefaultValue("beb7e03a-be7f-441d-b562-865f8fdc3aa9") UUID id){
        try{
            processCommandHandler.handle(new DeleteProcessCommand(id));
            return Response.status(Response.Status.OK).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (IllegalStateException e){
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @Operation(summary = "Concept process", description = "Concept process with the provided id")
    @DELETE
    @RolesAllowed("admin")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response conceptProcess(@QueryParam("process id") @DefaultValue("beb7e03a-be7f-441d-b562-865f8fdc3aa9") UUID id){
        try{
            processCommandHandler.handle(new ConceptProcessCommand(id));
            return Response.status(Response.Status.OK).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (IllegalStateException e){
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @Operation(summary = "Publish process", description = "Publish process with the provided id")
    @DELETE
    @RolesAllowed("admin")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response publishProcess(@QueryParam("process id") @DefaultValue("beb7e03a-be7f-441d-b562-865f8fdc3aa9") UUID id){
        try{
            processCommandHandler.handle(new PublishProcessCommand(id));
            return Response.status(Response.Status.OK).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (IllegalStateException e){
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
}
