package org.master.events.screen;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.master.model.screen.ScreenReadModel;
import org.master.repository.screen.ScreenReadRepository;

@ApplicationScoped
public class ScreenEventListener {

    @Inject
    ScreenReadRepository readRepository;

    @Incoming("screen-events")
    public void handleScreenEvent(ScreenEvent event) {
        if (ScreenEventType.ScreenCreated.equals(event.getType())) {
            ScreenReadModel readModel = new ScreenReadModel();
            readModel.setData(event.getData());
            readModel.setName(event.getName());
            readRepository.persist(readModel);
        } else if (ScreenEventType.ScreenUpdated.equals(event.getType())) {
            ScreenReadModel readModel = readRepository.findByUuid(event.getScreenId());
            if (readModel != null) {
                readModel.setData(event.getData());
                readModel.setName(event.getName());
                readRepository.persist(readModel);
            }
        }
    }
}
