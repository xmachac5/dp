package org.master.repository.screen;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.master.model.screen.ScreenReadModel;

import java.util.UUID;

@ApplicationScoped
public class ScreenReadRepository implements PanacheRepository<ScreenReadModel> {
    @Inject
    EntityManager em;

    public ScreenReadModel findByUuid(UUID uuid) {
        return em.find(ScreenReadModel.class, uuid);
    }
}
