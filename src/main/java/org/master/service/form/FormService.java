package org.master.service.form;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.master.dto.form.FormListDTO;
import org.master.model.form.FormReadModel;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class FormService {
    @Inject
    EntityManager em;

    public List<FormListDTO> getAllForms(){
        return em.createQuery(
                        "SELECT new org.master.dto.form.FormListDTO(f.id) FROM FormReadModel f", FormListDTO.class)
                .getResultList();
    }

    public FormReadModel findByUuid(UUID uuid) {
        return em.find(FormReadModel.class, uuid);
    }
}
