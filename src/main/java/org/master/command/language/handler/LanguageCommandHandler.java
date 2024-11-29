package org.master.command.language.handler;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.master.command.language.commands.UpdateAllowedCommand;
import org.master.model.language.Language;
import org.master.service.language.LanguageService;

@ApplicationScoped
public class LanguageCommandHandler {

    @Inject
    LanguageService languageService;

    public void handleUpdateAllowedCommand(UpdateAllowedCommand command){
        Language language = languageService.findByUuid(command.getId());
        if (language != null) {
            languageService.setAllowed(command.getId(), command.getAllowed());
        } else {
            throw new IllegalArgumentException("User not found with ID: " + command.getId());
        }
    }
}
