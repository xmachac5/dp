package org.master.query.screen;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.master.dto.screen.ScreenListDTO;
import org.master.model.screen.ScreenReadModel;
import org.master.query.screen.handler.ScreenQueryHandler;

import java.util.List;
import java.util.UUID;

@Path("/screens")
public class ScreenQueryResource {

    @Inject
    ScreenQueryHandler screenQUeryHandler;

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getScreenById(@PathParam("id") UUID id) {
        ScreenReadModel screenReadModel = screenQUeryHandler.getScreenById(id);
        if (screenReadModel == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(screenReadModel).build();
    }

    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getScreenList() {
        List<ScreenListDTO> screens = screenQUeryHandler.getScreenList();
        return Response.ok(screens).build();
    }
}
