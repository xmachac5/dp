package org.master.eventsourcing;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.master.events.BaseEvent;

@ApplicationScoped
public class ScreenEventPublisher {

    @Inject
    @Channel("screens")
    Emitter<String> eventEmitter;

    public void publish(BaseEvent event) {
        try {
            String eventPayload = new ObjectMapper().writeValueAsString(event);
            eventEmitter.send(eventPayload);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize event", e);
        }
    }
}
