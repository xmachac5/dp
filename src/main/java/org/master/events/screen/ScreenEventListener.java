package org.master.events.screen;

import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.master.dto.screen.ScreenCreateDTO;
import org.master.model.screen.ScreenReadModel;
import org.master.repository.screen.ScreenReadRepository;

@ApplicationScoped
public class ScreenEventListener {

    @Inject
    ScreenReadRepository readRepository;

    @Inject
    EntityManager em;

    @Incoming("screen-events")
    public void handleScreenEvent(ScreenEvent event) {
        if (ScreenEventType.ScreenCreated.equals(event.getType())) {
            ScreenCreateDTO screenCreateDTO = new ScreenCreateDTO();
            screenCreateDTO.setData(event.getData());
            screenCreateDTO.setName(event.getName());
            Log.info("test");
            readRepository.save(screenCreateDTO);
        } else if (ScreenEventType.ScreenUpdated.equals(event.getType())) {
            ScreenReadModel readModel = readRepository.findByUuid(event.getScreenId());
            if (readModel != null) {
                readModel.setData(event.getData());
                readModel.setName(event.getName());
                //readRepository.persist(readModel);
            }
        }
    }
}
