package org.master.query.screen.handler;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.master.dto.screen.ScreenListDTO;
import org.master.service.screen.ScreenService;

import java.util.List;

@ApplicationScoped
public class ScreenQUeryHandler {

    @Inject
    ScreenService screenService;

    public List<ScreenListDTO> getScreenList() {
        return screenService.getAllScreens();
    }
}
