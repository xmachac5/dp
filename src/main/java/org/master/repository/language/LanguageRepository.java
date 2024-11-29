package org.master.repository.language;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.master.model.language.Language;

import java.util.UUID;

@ApplicationScoped
public class LanguageRepository implements PanacheRepository<Language> {

    @Inject
    EntityManager em;

    public Language findByUuid(UUID uuid) {
        return em.find(Language.class, uuid);
    }

    @Transactional
    public void setAllowed(UUID uuid, Boolean allowed) {
        Language language = em.find(Language.class, uuid);
        if (language != null) {
            language.setAllowed(allowed);
        }
        em.merge(language);
    }
}
