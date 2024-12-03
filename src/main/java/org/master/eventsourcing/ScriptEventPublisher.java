package org.master.eventsourcing;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.master.events.BaseEvent;

@ApplicationScoped
public class ScriptEventPublisher {
    @Inject
    @Channel("scripts")
    Emitter<BaseEvent> eventEmitter;

    public void publish(BaseEvent event) {
        eventEmitter.send(event).toCompletableFuture().join();
    }
}