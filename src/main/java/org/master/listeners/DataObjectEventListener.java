package org.master.listeners;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.control.ActivateRequestContext;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.master.events.BaseEvent;
import org.master.events.dataObject.DataObjectCreatedEvent;
import org.master.events.dataObject.DataObjectDeletedEvent;
import org.master.events.dataObject.DataObjectUpdatedEvent;
import org.master.repository.dataObject.DataObjectReadRepository;

@ApplicationScoped
public class DataObjectEventListener {
    @Inject
    DataObjectReadRepository dataObjectReadRepository;

    @ActivateRequestContext
    @Incoming("form-commands")
    @Transactional
    public void consume(BaseEvent event) {
        if (event instanceof DataObjectCreatedEvent) {
            handle((DataObjectCreatedEvent) event);
        } else if (event instanceof DataObjectUpdatedEvent) {
            handle((DataObjectUpdatedEvent) event);
        } else if (event instanceof DataObjectDeletedEvent) {
            handle((DataObjectDeletedEvent) event);
        }
    }

    private void handle(DataObjectCreatedEvent event) {
        dataObjectReadRepository.create(event);
    }

    private void handle(DataObjectUpdatedEvent event) {
        dataObjectReadRepository.update(event);
    }

    private void handle(DataObjectDeletedEvent event) {
        dataObjectReadRepository.delete(event);
    }
}
