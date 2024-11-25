package org.master.eventsourcing;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.master.domain.AggregateRoot;
import org.master.events.BaseEvent;

import java.util.List;

@ApplicationScoped
public class EventStore {

    @Inject
    EntityManager em;

    @Inject ScreenEventPublisher screenEventPublisher;

    public void saveAndPublish(AggregateRoot aggregate) {
        List<BaseEvent> events = aggregate.getUncommittedChanges();
        for (BaseEvent event : events) {
            em.persist(event);
            screenEventPublisher.publish(event); // Publish the event
        }
        aggregate.markChangesAsCommitted();
    }
}
