package org.master.repository.language;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.master.model.language.Language;

@ApplicationScoped
public class LanguageRepository implements PanacheRepository<Language> {
}
