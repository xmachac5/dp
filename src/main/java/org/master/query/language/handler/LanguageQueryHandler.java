package org.master.query.language.handler;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.master.dto.language.LanguageListDTO;
import org.master.model.language.Language;
import org.master.service.language.LanguageService;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class LanguageQueryHandler {

    @Inject
    LanguageService languageService;

    public Language getLanguageByUuid(UUID uuid) {
        return languageService.findByUuid(uuid);
    }

    public List<LanguageListDTO> getLanguageList() {
        return languageService.getAllLanguages();
    }
}
