package org.master.query.screen;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.master.dto.screen.ScreenListDTO;
import org.master.query.screen.handler.ScreenQUeryHandler;

import java.util.List;

@Path("/screens")
public class ScreenQueryResource {

    @Inject
    ScreenQUeryHandler screenQUeryHandler;

    @GET
    @Path("/list")
    @RolesAllowed("admin")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getScreenList(@QueryParam("page") @DefaultValue("0") int page,
                                  @QueryParam("size") @DefaultValue("10") int size) {
        List<ScreenListDTO> screens = screenQUeryHandler.getScreenList();
        return Response.ok(screens).build();
    }
}
