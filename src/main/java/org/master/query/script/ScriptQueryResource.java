package org.master.query.script;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.master.dto.script.ScriptListDTO;
import org.master.model.script.ScriptReadModel;
import org.master.query.script.handler.ScriptQueryHandler;

import java.util.List;
import java.util.UUID;

@Path("scripts")
public class ScriptQueryResource {

    @Inject
    ScriptQueryHandler scriptQueryHandler;

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getScriptById(@PathParam("id") UUID id) {
        ScriptReadModel scriptReadModel = scriptQueryHandler.getScriptById(id);
        if (scriptReadModel == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(scriptReadModel).build();
    }

    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getScriptList() {
        List<ScriptListDTO> scripts = scriptQueryHandler.getScriptList();
        return Response.ok(scripts).build();
    }
}
