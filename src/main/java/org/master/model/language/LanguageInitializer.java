package org.master.model.language;

import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.master.repository.language.LanguageRepository;

import java.util.UUID;

@ApplicationScoped
public class LanguageInitializer {
    @Inject
    LanguageRepository languageRepository;

    @Transactional
    public void onStart(@Observes StartupEvent ev) {
        if (languageRepository.count() == 0) {
            languageRepository.persist(new Language(UUID.randomUUID(),"English", "en", true));
            languageRepository.persist(new Language(UUID.randomUUID(),"Czech", "cs", true));
            languageRepository.persist(new Language(UUID.randomUUID(),"German", "ge", false));
            languageRepository.persist(new Language(UUID.randomUUID(),"Slovak", "sk", false));
        }
    }
}
