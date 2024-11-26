package org.master.eventsourcing;

import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.master.events.BaseEvent;

@ApplicationScoped
public class ScreenEventPublisher {

    @Inject
    @Channel("screens")
    Emitter<BaseEvent> eventEmitter;

    public void publish(BaseEvent event) {
        eventEmitter.send(event).toCompletableFuture().join();
    }
}
