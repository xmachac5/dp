package org.master.repository.screen;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.master.dto.screen.ScreenCreateDTO;
import org.master.model.screen.ScreenReadModel;
import org.master.service.screen.ScreenService;

import java.util.UUID;

@ApplicationScoped
public class ScreenReadRepository implements PanacheRepository<ScreenReadModel> {
    @Inject
    EntityManager em;

    @Inject
    ScreenService screenService;

    public ScreenReadModel findByUuid(UUID uuid) {
        return em.find(ScreenReadModel.class, uuid);
    }


    public void save(ScreenCreateDTO screenCreateDTO) {
        screenService.createReadScreen(screenCreateDTO);
    }
}
