package org.master.service.language;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.master.dto.language.LanguageListDTO;
import org.master.model.language.Language;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class LanguageService {

    @Inject
    EntityManager em;

    public Language findByUuid(UUID uuid) {
        return em.find(Language.class, uuid);
    }

    public List<LanguageListDTO> getAllLanguages() {
        return em.createQuery(
                        "SELECT new org.master.dto.language.LanguageListDTO(l.id, l.code, l.allowed) FROM Language l", LanguageListDTO.class)
                .getResultList();
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
