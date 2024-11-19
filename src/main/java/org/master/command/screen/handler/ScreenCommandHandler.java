package org.master.command.screen.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.master.dto.screen.ScreenCreateDTO;
import org.master.service.screen.ScreenService;

@ApplicationScoped
public class ScreenCommandHandler {

    @Inject
    ScreenService screenService;

    @Inject
    ObjectMapper objectMapper; // Inject Jackson's ObjectMapper for JSON conversion

    public void handleCreateScreenCommand(ScreenCreateDTO screenCreateDTO){

        if (screenService.findByName(screenCreateDTO.getName()) != null) {
            throw new RuntimeException("Name already exists: " + screenCreateDTO.getName());
        }

        // Create the user
        screenService.createScreen(screenCreateDTO);

    }
}
