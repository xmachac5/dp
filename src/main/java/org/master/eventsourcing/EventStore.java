package org.master.eventsourcing;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.master.domain.screen.ScreenAggregate;
import org.master.events.BaseEvent;
import org.master.events.screen.ScreenEventSerializer;
import org.master.model.Event;
import org.master.model.screen.ScreenWriteModel;

import java.util.List;

@ApplicationScoped
public class EventStore {

    @Inject
    EntityManager em;

    @Inject ScreenEventPublisher screenEventPublisher;

    @Inject
    ScreenEventSerializer screenEventSerializer;

    @Transactional
    public void saveAndPublish(ScreenAggregate aggregate) {
        List<BaseEvent> events = aggregate.getUncommittedChanges();
        for (BaseEvent event : events) {
            // Serialize the BaseEvent to JSON
            String payload = screenEventSerializer.serialize(event);

            // Persist the Event
            Event persistentEvent = new Event(event, payload, aggregate.getVersion());
            em.persist(persistentEvent);

            // Publish the Event
            screenEventPublisher.publish(event);
        }
        ScreenWriteModel screenWriteModel = new ScreenWriteModel();
        screenWriteModel.setName(aggregate.getName());
        screenWriteModel.setData(aggregate.getData());

        em.persist(screenWriteModel);

        // Mark changes as committed
        aggregate.markChangesAsCommitted();
    }
}
