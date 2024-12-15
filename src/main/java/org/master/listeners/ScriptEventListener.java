package org.master.listeners;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.control.ActivateRequestContext;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.master.events.BaseEvent;
import org.master.events.script.ScriptCreatedEvent;
import org.master.events.script.ScriptDeletedEvent;
import org.master.events.script.ScriptPublishedEvent;
import org.master.events.script.ScriptUpdatedEvent;
import org.master.repository.script.ScriptReadRepository;

@ApplicationScoped
public class ScriptEventListener {

    @Inject
    ScriptReadRepository readRepository;

    @ActivateRequestContext
    @Incoming("script-commands")
    @Transactional
    public void consume(BaseEvent event) {
        if (event instanceof ScriptCreatedEvent) {
            handle((ScriptCreatedEvent) event);
        } else if (event instanceof ScriptUpdatedEvent) {
            handle((ScriptUpdatedEvent) event);
        } else if (event instanceof ScriptDeletedEvent) {
            handle((ScriptDeletedEvent) event);
        } else if (event instanceof ScriptPublishedEvent) {
            handle((ScriptPublishedEvent) event);
        }
    }

    private void handle(ScriptCreatedEvent event) {
        readRepository.create(event);
    }

    private void handle(ScriptUpdatedEvent event) {
    }

    private void handle(ScriptDeletedEvent event) {
        readRepository.delete(event);
    }

    private void handle(ScriptPublishedEvent event) {
        readRepository.update(event);
    }
}
