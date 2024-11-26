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

    @Inject
    EntityManager em;

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
        ScreenReadModel screenReadModel = new ScreenReadModel();
        screenReadModel.setId(event.getId());
        screenReadModel.setName(event.getName());
        screenReadModel.setData(event.getData());
        em.persist(screenReadModel); // Update read model
    }

    private void handle(ScreenUpdatedEvent event) {
        ScreenReadModel screenReadModel = em.find(ScreenReadModel.class, event.getId());
        screenReadModel.setName(event.getName());
        screenReadModel.setData(event.getData());
        em.merge(screenReadModel); // Update read model
    }
}
