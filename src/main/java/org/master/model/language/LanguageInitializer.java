package org.master.model.language;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.master.repository.language.LanguageRepository;

@ApplicationScoped
public class LanguageInitializer {
    @Inject
    LanguageRepository languageRepository;

    @PostConstruct
    @Transactional
    public void init() {
        if (languageRepository.count() == 0) {
            languageRepository.persist(new Language("English", "en", true));
            languageRepository.persist(new Language("Czech", "cs", true));
            languageRepository.persist(new Language("German", "ge", false));
            languageRepository.persist(new Language("Slovak", "sk", false));
        }
    }
}
