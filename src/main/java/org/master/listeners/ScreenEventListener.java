package org.master.listeners;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.control.ActivateRequestContext;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.master.events.BaseEvent;
import org.master.events.screen.ScreenCreatedEvent;
import org.master.events.screen.ScreenUpdatedEvent;
import org.master.model.screen.ScreenReadModel;
import org.master.repository.screen.ScreenReadRepository;


@ApplicationScoped
public class ScreenEventListener {

    @Inject
    ScreenReadRepository readRepository;

    @ActivateRequestContext
    @Incoming("screen-commands")
    @Transactional
    public void consume(BaseEvent event) {
        if (event instanceof ScreenCreatedEvent) {
            handle((ScreenCreatedEvent) event);
        } else if (event instanceof ScreenUpdatedEvent) {
            handle((ScreenUpdatedEvent) event);
        }
    }

    private void handle(ScreenCreatedEvent event) {
        readRepository.create(event);
    }

    private void handle(ScreenUpdatedEvent event) {
        readRepository.update(event);
    }
}
