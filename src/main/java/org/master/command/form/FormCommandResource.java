package org.master.command.form;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.master.command.form.commands.*;
import org.master.command.form.handler.FormCommandHandler;
import org.master.dto.form.FormCreateDTO;
import org.master.dto.form.FormCreateWithDODTO;
import org.master.dto.form.FormUpdateDTO;
import org.master.dto.form.FormUpdateWithDODTO;

import java.util.UUID;

@Path("/form")
@Tag(name = "Form Commands", description = "Handles commands related to form creation")
public class FormCommandResource {

    @Inject
    FormCommandHandler formCommandHandler;

    @Operation(summary = "Create a new form", description = "Creates a form with the provided data")
    @POST
    @RolesAllowed("admin")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createForm(@Valid FormCreateDTO formCreateDTO){
        formCommandHandler.handle(new CreateFormCommand(
                formCreateDTO.getName(),
                formCreateDTO.getColumns(),
                formCreateDTO.getRowHeights(),
                formCreateDTO.getPrimaryLanguageId(),
                formCreateDTO.getRowMaxHeights(),
                formCreateDTO.getColumnMapping(),
                formCreateDTO.getDefinition(),
                null
        ));
        return Response.status(Response.Status.CREATED).build();
    }

    @Operation(summary = "Update form", description = "Update form with the provided data")
    @PUT
    @RolesAllowed("admin")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateForm(@QueryParam("form id") @DefaultValue("beb7e03a-be7f-441d-b562-865f8fdc3aa9") UUID id,
                                 @Valid FormUpdateDTO formUpdateDTO){
        try {
            formCommandHandler.handle(new UpdateFormCommand(
                    id,
                    formUpdateDTO.getColumns(),
                    formUpdateDTO.getRowHeights(),
                    formUpdateDTO.getPrimaryLanguageId(),
                    formUpdateDTO.getRowMaxHeights(),
                    formUpdateDTO.getColumnMapping(),
                    formUpdateDTO.getDefinition(),
                    null
            ));
            return Response.status(Response.Status.OK).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (IllegalStateException e){
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @Operation(summary = "Publish form", description = "Publish form with the provided data")
    @PUT
    @Path("/publish")
    @RolesAllowed("admin")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response publishForm(@QueryParam("form id") @DefaultValue("beb7e03a-be7f-441d-b562-865f8fdc3aa9") UUID id){
        try {
            formCommandHandler.handle(new PublishFormCommand(id));
            return Response.status(Response.Status.OK).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (IllegalStateException e){
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @Operation(summary = "Create a new form with data object", description = "Creates a form and data object with the provided data")
    @POST
    @Path("/do")
    @RolesAllowed("admin")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createFormWithDO(@Valid FormCreateWithDODTO formCreateWithDODTO){
        formCommandHandler.handle(new CreateFormWithDOCommand(
                formCreateWithDODTO.getName(),
                formCreateWithDODTO.getColumns(),
                formCreateWithDODTO.getRowHeights(),
                formCreateWithDODTO.getPrimaryLanguageId(),
                formCreateWithDODTO.getRowMaxHeights(),
                formCreateWithDODTO.getColumnMapping(),
                formCreateWithDODTO.getDefinition(),
                formCreateWithDODTO.getTrackChanges(),
                formCreateWithDODTO.getSoftDelete()
        ));
        return Response.status(Response.Status.CREATED).build();
    }

    @Operation(summary = "Update form with nev version of data object", description = "Update form with the provided data and creates new version of data object")
    @PUT
    @Path("/do")
    @RolesAllowed("admin")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateScreenWithDO(@QueryParam("form id") @DefaultValue("beb7e03a-be7f-441d-b562-865f8fdc3aa9") UUID id,
                                 @Valid FormUpdateWithDODTO formUpdateWithDODTO){
        try {
            formCommandHandler.handle(new UpdateFormWithDOCommand(
                    id,
                    formUpdateWithDODTO.getColumns(),
                    formUpdateWithDODTO.getRowHeights(),
                    formUpdateWithDODTO.getPrimaryLanguageId(),
                    formUpdateWithDODTO.getRowMaxHeights(),
                    formUpdateWithDODTO.getColumnMapping(),
                    formUpdateWithDODTO.getDefinition(),
                    formUpdateWithDODTO.getTrackChanges(),
                    formUpdateWithDODTO.getSoftDelete()
            ));
            return Response.status(Response.Status.OK).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (IllegalStateException e){
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @Operation(summary = "Publish form with DO", description = "Publish form with DO with the provided data")
    @PUT
    @Path("/do/publish")
    @RolesAllowed("admin")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response publishFormWithDO(@QueryParam("form id") @DefaultValue("beb7e03a-be7f-441d-b562-865f8fdc3aa9") UUID id){
        try {
            formCommandHandler.handle(new PublishFormWithDOCommand(id));
            return Response.status(Response.Status.OK).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (IllegalStateException e){
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @Operation(summary = "Delete form", description = "Delete form with the provided data")
    @DELETE
    @RolesAllowed("admin")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteForm(@QueryParam("form id") @DefaultValue("beb7e03a-be7f-441d-b562-865f8fdc3aa9") UUID id){
        try {
            formCommandHandler.handle(new DeleteFormCommand(id));
            return Response.status(Response.Status.OK).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (IllegalStateException e){
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @Operation(summary = "Delete form with data object", description = "Delete form and connected data object by the given id")
    @DELETE
    @Path("/do")
    @RolesAllowed("admin")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteFormWithDO(@QueryParam("form id") @DefaultValue("beb7e03a-be7f-441d-b562-865f8fdc3aa9") UUID id){
        try {
            formCommandHandler.handle(new DeleteFormWithDOCommand(id));
            return Response.status(Response.Status.OK).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (IllegalStateException e){
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
}
