package org.master.service.screen;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import org.master.dto.screen.ScreenListDTO;
import org.master.model.screen.ScreenReadModel;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class ScreenService {

    @Inject
    EntityManager em;

    public List<ScreenListDTO> getAllScreens(){
        return em.createQuery(
                        "SELECT new org.master.dto.screen.ScreenListDTO(s.id, s.title, s.data) FROM ScreenReadModel s", ScreenListDTO.class)
                .getResultList();
    }

    public ScreenReadModel findByUuid(UUID uuid) {
        return em.find(ScreenReadModel.class, uuid);
    }
}
