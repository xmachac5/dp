package org.master.query.screen.handler;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.master.dto.screen.ScreenListDTO;
import org.master.model.screen.ScreenReadModel;
import org.master.service.screen.ScreenService;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class ScreenQueryHandler {

    @Inject
    ScreenService screenService;

    public List<ScreenListDTO> getScreenList() {
        return screenService.getAllScreens();
    }
    public ScreenReadModel getScreenById(UUID id) {
        return screenService.findByUuid(id);
    }
}
