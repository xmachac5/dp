package org.master.query.form;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.master.dto.form.FormListDTO;
import org.master.model.form.FormReadModel;
import org.master.query.form.handler.FormQueryHandler;

import java.util.List;
import java.util.UUID;

@Path("/forms")
public class FormQueryResource {

    @Inject
    FormQueryHandler formQueryHandler;

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFormById(@PathParam("id") UUID id) {
        FormReadModel formReadModel = formQueryHandler.getFormById(id);
        if (formReadModel == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(formReadModel).build();
    }

    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFormList() {
        List<FormListDTO> forms = formQueryHandler.getFormList();
        return Response.ok(forms).build();
    }
}
