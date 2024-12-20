package org.master.query.user;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.master.dto.user.DetailUserDTO;
import org.master.dto.user.UserListDTO;
import org.master.query.user.handler.UserQueryHandler;

import java.util.List;
import java.util.UUID;

@Path("/users")
public class UserQueryResource {

    @Inject
    UserQueryHandler userQueryHandler;

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserById(@PathParam("id") UUID uuid) {
        DetailUserDTO user = userQueryHandler.getUserByUuid(uuid);
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(user).build();
    }

    @GET
    @RolesAllowed("admin")
    @Path("/current")
    public Response getCurrentUser() {
        DetailUserDTO user = userQueryHandler.getCurrentUser();
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(user).build();
    }

    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserList() {
        List<UserListDTO> users = userQueryHandler.getUserList();
        return Response.ok(users).build();
    }
}
