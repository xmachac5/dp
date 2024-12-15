package org.master.listeners;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.control.ActivateRequestContext;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.master.events.BaseEvent;
import org.master.events.screen.ScreenCreatedEvent;
import org.master.events.screen.ScreenDeletedEvent;
import org.master.events.screen.ScreenPublishedEvent;
import org.master.events.screen.ScreenUpdatedEvent;
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
        } else if (event instanceof ScreenDeletedEvent) {
            handle((ScreenDeletedEvent) event);
        } else if (event instanceof ScreenPublishedEvent) {
            handle((ScreenPublishedEvent) event);
        }
    }

    private void handle(ScreenCreatedEvent event) {
        readRepository.create(event);
    }

    private void handle(ScreenUpdatedEvent event) {
    }

    private void handle(ScreenDeletedEvent event) {
        readRepository.delete(event);
    }

    private void handle(ScreenPublishedEvent event) {
        readRepository.update(event);
    }
}
