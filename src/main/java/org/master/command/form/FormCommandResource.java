package org.master.command.form;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.master.command.form.commands.CreateFormCommand;
import org.master.command.form.commands.DeleteFormCommand;
import org.master.command.form.commands.UpdateFormCommand;
import org.master.command.form.handler.FormCommandHandler;
import org.master.dto.form.FormCreateDTO;
import org.master.dto.form.FormUpdateDTO;

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
                formCreateDTO.getDefinition()
        ));
        return Response.status(Response.Status.CREATED).build();
    }

    @Operation(summary = "Update form", description = "Update form with the provided data")
    @PUT
    @RolesAllowed("admin")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateScreen(@QueryParam("form id") @DefaultValue("beb7e03a-be7f-441d-b562-865f8fdc3aa9") UUID id,
                                 @Valid FormUpdateDTO formUpdateDTO){
        try {
            formCommandHandler.handle(new UpdateFormCommand(
                    id,
                    formUpdateDTO.getColumns(),
                    formUpdateDTO.getRowHeights(),
                    formUpdateDTO.getPrimaryLanguageId(),
                    formUpdateDTO.getRowMaxHeights(),
                    formUpdateDTO.getColumnMapping(),
                    formUpdateDTO.getDefinition()
            ));
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
    public Response deleteScreen(@QueryParam("form id") @DefaultValue("beb7e03a-be7f-441d-b562-865f8fdc3aa9") UUID id){
        try {
            formCommandHandler.handle(new DeleteFormCommand(id));
            return Response.status(Response.Status.OK).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (IllegalStateException e){
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
}