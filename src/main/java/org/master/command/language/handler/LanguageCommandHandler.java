package org.master.command.language.handler;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.master.command.language.commands.UpdateAllowedCommand;
import org.master.model.language.Language;
import org.master.repository.language.LanguageRepository;
import org.master.service.language.LanguageService;

@ApplicationScoped
public class LanguageCommandHandler {

    @Inject
    LanguageRepository languageRepository;

    public void handleUpdateAllowedCommand(UpdateAllowedCommand command){
        Language language = languageRepository.findByUuid(command.getId());
        if (language != null) {
            languageRepository.setAllowed(command.getId(), command.getAllowed());
        } else {
            throw new IllegalArgumentException("Language not found with ID: " + command.getId());
        }
    }
}
