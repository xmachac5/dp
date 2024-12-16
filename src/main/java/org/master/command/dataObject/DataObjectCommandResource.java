package org.master.command.dataObject;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.master.command.dataObject.commands.CreateDataObjectCommand;
import org.master.command.dataObject.commands.DeleteDataObjectCommand;
import org.master.command.dataObject.commands.PublishDataObjectCommand;
import org.master.command.dataObject.commands.UpdateDataObjectCommand;
import org.master.command.dataObject.handler.DataObjectCommandHandler;
import org.master.dto.dataObject.DataObjectCreateDTO;
import org.master.dto.dataObject.DataObjectUpdateDTO;

import java.util.UUID;

@Path("/dataObject")
@Tag(name = "Data object Commands", description = "Handles commands related to data object creation")
public class DataObjectCommandResource {

    @Inject
    DataObjectCommandHandler dataObjectCommandHandler;

    @Operation(summary = "Create a new data object", description = "Creates a data object with the provided data")
    @POST
    @RolesAllowed("admin")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createDataObject(@Valid DataObjectCreateDTO dataObjectCreateDTO){
        try {
            UUID newDO = dataObjectCommandHandler.handle(new CreateDataObjectCommand(
                    dataObjectCreateDTO.getName(),
                    dataObjectCreateDTO.getDescription(),
                    dataObjectCreateDTO.getTrackChanges(),
                    dataObjectCreateDTO.getSoftDelete(),
                    dataObjectCreateDTO.getColumns(),
                    false
            ));
            return Response.status(Response.Status.CREATED)
                    .entity(newDO)
                    .build();
        }
        catch (IllegalArgumentException e){
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @Operation(summary = "Update data object", description = "Update data object with the provided data")
    @PUT
    @RolesAllowed("admin")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateScreen(@QueryParam("data object id") @DefaultValue("beb7e03a-be7f-441d-b562-865f8fdc3aa9") UUID id,
                                 @Valid DataObjectUpdateDTO dataObjectUpdateDTO){
        try {
            dataObjectCommandHandler.handle(new UpdateDataObjectCommand(
                    id,
                    dataObjectUpdateDTO.getDescription(),
                    dataObjectUpdateDTO.getTrackChanges(),
                    dataObjectUpdateDTO.getSoftDelete(),
                    dataObjectUpdateDTO.getColumns()
            ));
            return Response.status(Response.Status.OK).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (IllegalStateException e){
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @Operation(summary = "Delete data object", description = "Delete data object with the provided data")
    @DELETE
    @RolesAllowed("admin")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteScreen(@QueryParam("data object id") @DefaultValue("beb7e03a-be7f-441d-b562-865f8fdc3aa9") UUID id){
        try {
            dataObjectCommandHandler.handle(new DeleteDataObjectCommand(id));
            return Response.status(Response.Status.OK).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (IllegalStateException e){
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @Operation(summary = "Publish data object", description = "Publish data object with the provided data")
    @PUT
    @Path("/publish")
    @RolesAllowed("admin")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response publishDataObject(@QueryParam("data object id") @DefaultValue("beb7e03a-be7f-441d-b562-865f8fdc3aa9") UUID id){
        try {
            dataObjectCommandHandler.handle(new PublishDataObjectCommand(id));
            return Response.status(Response.Status.OK).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (IllegalStateException e){
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
}
