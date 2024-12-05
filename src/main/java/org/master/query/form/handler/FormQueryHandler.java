package org.master.query.form.handler;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.master.dto.form.FormListDTO;
import org.master.model.form.FormReadModel;
import org.master.service.form.FormService;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class FormQueryHandler {

    @Inject
    FormService formService;

    public List<FormListDTO> getFormList() {
        return formService.getAllForms();
    }
    public FormReadModel getFormById(UUID id) {
        return formService.findByUuid(id);
    }
}
