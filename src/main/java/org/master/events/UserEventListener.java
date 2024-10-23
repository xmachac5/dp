package org.master.events;

import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Incoming;

@ApplicationScoped
public class UserEventListener {

    @Incoming("user-events")
    public void consumeUserEvent(String message) {
        System.out.println("Received Kafka message: " + message);
        // Process the event here (e.g., log it or trigger further actions)
    }
}
