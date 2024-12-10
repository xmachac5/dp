package org.master.listeners;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.control.ActivateRequestContext;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.master.events.BaseEvent;
import org.master.events.process.ProcessCreatedEvent;
import org.master.events.process.ProcessDeletedEvent;
import org.master.events.process.ProcessPublishedEvent;
import org.master.events.process.ProcessUpdatedEvent;
import org.master.repository.process.ProcessReadRepository;

@ApplicationScoped
public class ProcessEventListener {
    @Inject
    ProcessReadRepository processReadRepository;

    @ActivateRequestContext
    @Incoming("process-commands")
    @Transactional
    public void consume(BaseEvent event) {
        if (event instanceof ProcessCreatedEvent) {
            handle((ProcessCreatedEvent) event);
        } else if (event instanceof ProcessUpdatedEvent) {
            handle((ProcessUpdatedEvent) event);
        } else if (event instanceof ProcessDeletedEvent) {
            handle((ProcessDeletedEvent) event);
        } else if (event instanceof ProcessPublishedEvent) {
            handle((ProcessPublishedEvent) event);
        }
    }

    private void handle(ProcessCreatedEvent event) {
        processReadRepository.create(event);
    }

    private void handle(ProcessUpdatedEvent event) {
    }

    private void handle(ProcessDeletedEvent event) {
        processReadRepository.delete(event);
    }

    private void handle(ProcessPublishedEvent event){
        processReadRepository.publish(event);
    }
}

