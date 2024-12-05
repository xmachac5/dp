package org.master.eventsourcing;

import org.master.events.BaseEvent;

public interface EventPublisher {
    void publish(BaseEvent event);
}
