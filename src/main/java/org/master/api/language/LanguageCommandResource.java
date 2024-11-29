package org.master.api.language;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.master.command.language.commands.UpdateAllowedCommand;
import org.master.command.language.handler.LanguageCommandHandler;
import org.master.dto.language.LanguageUpdateAllowedDTO;

@Path("/languages")
@Tag(name = "Languages Commands", description = "Handles commands related to changing language allowed property")
public class LanguageCommandResource {

    @Inject
    LanguageCommandHandler languageCommandHandler;

    @PUT
    @Path("/allowed-true")
    @RolesAllowed("admin")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response setAllowedTrue(LanguageUpdateAllowedDTO languageUpdateAllowedDTO) {
        return setAllowed(languageUpdateAllowedDTO);
    }

    @PUT
    @Path("/allowed-false")
    @RolesAllowed("admin")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response setAllowedFalse(LanguageUpdateAllowedDTO languageUpdateAllowedDTO) {
        return setAllowed(languageUpdateAllowedDTO);
    }

    private Response setAllowed(LanguageUpdateAllowedDTO languageUpdateAllowedDTO) {
        try {
            UpdateAllowedCommand command = new UpdateAllowedCommand(languageUpdateAllowedDTO.getId(), languageUpdateAllowedDTO.getAllowed());
            languageCommandHandler.handleUpdateAllowedCommand(command);
            return Response.ok("Allowed set to true successfully").build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }
}
