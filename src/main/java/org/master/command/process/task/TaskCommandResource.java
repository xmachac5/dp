package org.master.command.process.task;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.master.command.process.task.commands.*;
import org.master.command.process.task.handler.TaskCommandHandler;
import org.master.dto.process.task.*;

import java.util.UUID;

@Path("/tasks")
@Tag(name = "Tasks Commands", description = "Handles commands related to tasks")
public class TaskCommandResource {
    @Inject
    TaskCommandHandler taskCommandHandler;

    @Operation(summary = "Create a new DO task", description = "Creates a DO task with the provided data")
    @POST
    @RolesAllowed("admin")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createDOTask(@Valid DOTaskCreateDTO DOTaskCreateDTO){
        taskCommandHandler.handle(new CreateDOTaskCommand(
                DOTaskCreateDTO.getName(),
                DOTaskCreateDTO.getVariableMapping(),
                DOTaskCreateDTO.getColumnsMapping(),
                DOTaskCreateDTO.getDataObjectsWriteModel(),
                DOTaskCreateDTO.getProcessVersionWriteModel()));
        return Response.status(Response.Status.CREATED).build();
    }

    @Operation(summary = "Create a new Screen task", description = "Creates a Screen task with the provided data")
    @POST
    @RolesAllowed("admin")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createScreenTask(@Valid ScreenTaskCreateDTO ScreenTaskCreateDTO){
        taskCommandHandler.handle(new CreateScreenTaskCommand(
                ScreenTaskCreateDTO.getName(),
                ScreenTaskCreateDTO.getVariableMapping(),
                ScreenTaskCreateDTO.getScreenWriteModel(),
                ScreenTaskCreateDTO.getProcessVersionWriteModel()));
        return Response.status(Response.Status.CREATED).build();
    }

    @Operation(summary = "Create a new Script task", description = "Creates a Script task with the provided data")
    @POST
    @RolesAllowed("admin")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createScriptTask(@Valid ScriptTaskCreateDTO scriptTaskCreateDTO){
        taskCommandHandler.handle(new CreateScriptTaskCommand(
                scriptTaskCreateDTO.getName(),
                scriptTaskCreateDTO.getVariableMapping(),
                scriptTaskCreateDTO.getScriptWriteModel(),
                scriptTaskCreateDTO.getProcessVersionWriteModel()));
        return Response.status(Response.Status.CREATED).build();
    }

    @Operation(summary = "Update DO task", description = "Update DO task with the provided data")
    @PUT
    @RolesAllowed("admin")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateDOTask(@QueryParam("task id") @DefaultValue("beb7e03a-be7f-441d-b562-865f8fdc3aa9") UUID id,
                                 @Valid DOTaskUpdateDTO doTaskUpdateDTO){
        try{
            taskCommandHandler.handle(new UpdateDOTaskCommand(id,
                    doTaskUpdateDTO.getName(),
                    doTaskUpdateDTO.getVariableMapping(),
                    doTaskUpdateDTO.getColumnsMapping(),
                    doTaskUpdateDTO.getDataObjectsWriteModel()));
            return Response.status(Response.Status.OK).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (IllegalStateException e){
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @Operation(summary = "Update Screen task", description = "Update Screen task with the provided data")
    @PUT
    @RolesAllowed("admin")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateScreenTask(@QueryParam("task id") @DefaultValue("beb7e03a-be7f-441d-b562-865f8fdc3aa9") UUID id,
                                 @Valid ScreenTaskUpdateDTO screenTaskUpdateDTO){
        try{
            taskCommandHandler.handle(new UpdateScreenTaskCommand(id,
                    screenTaskUpdateDTO.getName(),
                    screenTaskUpdateDTO.getVariableMapping(),
                    screenTaskUpdateDTO.getScreenWriteModel()));
            return Response.status(Response.Status.OK).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (IllegalStateException e){
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @Operation(summary = "Update script task", description = "Update script task with the provided data")
    @PUT
    @RolesAllowed("admin")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateScriptTask(@QueryParam("task id") @DefaultValue("beb7e03a-be7f-441d-b562-865f8fdc3aa9") UUID id,
                                     @Valid ScriptTaskUpdateDTO scriptTaskUpdateDTO){
        try{
            taskCommandHandler.handle(new UpdateScriptTaskCommand(id,
                    scriptTaskUpdateDTO.getName(),
                    scriptTaskUpdateDTO.getVariableMapping(),
                    scriptTaskUpdateDTO.getScriptWriteModel()));
            return Response.status(Response.Status.OK).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (IllegalStateException e){
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @Operation(summary = "Delete task", description = "Delete task with the provided data")
    @DELETE
    @RolesAllowed("admin")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteTask(@QueryParam("task id") @DefaultValue("beb7e03a-be7f-441d-b562-865f8fdc3aa9") UUID id){
        try{
            taskCommandHandler.handle(new DeleteTaskCommand(id));
            return Response.status(Response.Status.OK).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (IllegalStateException e){
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
}
