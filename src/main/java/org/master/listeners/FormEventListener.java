package org.master.listeners;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.control.ActivateRequestContext;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.master.events.BaseEvent;
import org.master.events.form.FormCreatedEvent;
import org.master.events.form.FormDeletedEvent;
import org.master.events.form.FormUpdatedEvent;
import org.master.repository.form.FormReadRepository;

@ApplicationScoped
public class FormEventListener {
    @Inject
    FormReadRepository formReadRepository;

    @ActivateRequestContext
    @Incoming("form-commands")
    @Transactional
    public void consume(BaseEvent event) {
        if (event instanceof FormCreatedEvent) {
            handle((FormCreatedEvent) event);
        } else if (event instanceof FormUpdatedEvent) {
            handle((FormUpdatedEvent) event);
        } else if (event instanceof FormDeletedEvent) {
            handle((FormDeletedEvent) event);
        }
    }

    private void handle(FormCreatedEvent event) {
        formReadRepository.create(event);
    }

    private void handle(FormUpdatedEvent event) {
        formReadRepository.update(event);
    }

    private void handle(FormDeletedEvent event) {
        formReadRepository.delete(event);
    }
}
