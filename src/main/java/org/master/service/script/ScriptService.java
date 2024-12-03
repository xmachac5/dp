package org.master.service.script;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.master.dto.script.ScriptListDTO;
import org.master.model.script.ScriptReadModel;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class ScriptService {

    @Inject
    EntityManager em;

    public List<ScriptListDTO> getAllScripts(){
        return em.createQuery(
                        "SELECT new org.master.dto.script.ScriptListDTO(s.id) FROM ScriptReadModel s", ScriptListDTO.class)
                .getResultList();
    }

    public ScriptReadModel findByUuid(UUID uuid) {
        return em.find(ScriptReadModel.class, uuid);
    }
}
