package org.master.query.dataObject;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.master.dto.dataObject.DataObjectListDTO;
import org.master.model.dataObject.DataObjectsReadModel;
import org.master.query.dataObject.handler.DataObjectQueryHandler;

import java.util.List;
import java.util.UUID;

@Path("/dataObjects")
public class DataObjectQueryResource {
    @Inject
    DataObjectQueryHandler dataObjectQueryHandler;

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDataObjectById(@PathParam("id") UUID id) {
        DataObjectsReadModel dataObjectsReadModel = dataObjectQueryHandler.getDataObjectById(id);
        if (dataObjectsReadModel == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(dataObjectsReadModel).build();
    }

    @GET
    @Path("/list")
    @RolesAllowed("admin")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDataObjectList() {
        List<DataObjectListDTO> dataObjects = dataObjectQueryHandler.getDataObjectList();
        return Response.ok(dataObjects).build();
    }
}
