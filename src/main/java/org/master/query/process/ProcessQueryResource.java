package org.master.query.process;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.master.dto.process.ProcessDetailDTO;
import org.master.dto.process.ProcessListDTO;
import org.master.query.process.handler.ProcessQueryHandler;

import java.util.List;
import java.util.UUID;

@Path("/processes")
public class ProcessQueryResource {
    @Inject
    ProcessQueryHandler processQueryHandler;

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProcessById(@PathParam("id") UUID id) {
        ProcessDetailDTO processDetailDTO = processQueryHandler.getProcessById(id);
        if (processDetailDTO == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(processDetailDTO).build();
    }

    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProcessList() {
        List<ProcessListDTO> forms = processQueryHandler.getProcessList();
        return Response.ok(forms).build();
    }
}
