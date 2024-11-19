package org.master.events.screen;

import com.fasterxml.jackson.databind.JsonNode;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import java.time.LocalDateTime;
import java.util.UUID;

@ApplicationScoped
public class ScreenEventPublisher {

    @Inject
    @Channel("screens")
    Emitter<ScreenEvent> eventEmitter;

    public void publish(ScreenEventType type, UUID screenId, String name, JsonNode data) {
        ScreenEvent event = new ScreenEvent();
        event.setType(type);
        event.setScreenId(screenId);
        event.setTimestamp(LocalDateTime.now());
        event.setName(name);
        event.setData(data);
        eventEmitter.send(event)
                .toCompletableFuture()
                .join();
        Log.info("test_publish");
    }
}
