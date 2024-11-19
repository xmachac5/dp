package org.master.events.screen;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.json.Json;
import jakarta.json.JsonString;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import java.time.LocalDateTime;
import java.util.UUID;

@ApplicationScoped
public class ScreenEventPublisher {

    @Channel("screen-commands")
    Emitter<ScreenEvent> eventEmitter;

    public void publish(ScreenEventType type, UUID screenId, String name, JsonNode data) {
        ScreenEvent event = new ScreenEvent();
        event.setType(type);
        event.setScreenId(screenId);
        event.setTimestamp(LocalDateTime.now());
        event.setName(name);
        event.setData(data);

        eventEmitter.send(event);
    }
}
