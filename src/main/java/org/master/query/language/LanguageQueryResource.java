package org.master.query.language;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.master.dto.language.LanguageListDTO;
import org.master.query.language.handler.LanguageQueryHandler;
import org.master.model.language.Language;

import java.util.List;
import java.util.UUID;

@Path("/languages")
public class LanguageQueryResource {
    @Inject
    LanguageQueryHandler languageQueryHandler;

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLanguageById(@PathParam("id") UUID uuid) {
        Language language = languageQueryHandler.getLanguageByUuid(uuid);
        if (language == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(language).build();
    }

    @GET
    @Path("/list")
    @RolesAllowed("admin")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLanguageList() {
        List<LanguageListDTO> languages = languageQueryHandler.getLanguageList();
        return Response.ok(languages).build();
    }
}
