package org.master.eventsourcing;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.master.domain.screen.ScreenAggregate;
import org.master.domain.script.ScriptAggregate;
import org.master.events.BaseEvent;
import org.master.events.EventDeserializer;
import org.master.events.EventSerializer;
import org.master.model.Event;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
public class EventStore {

    @Inject
    EntityManager em;

    @Inject ScreenEventPublisher screenEventPublisher;

    @Inject ScriptEventPublisher scriptEventPublisher;

    @Inject
    EventSerializer eventSerializer;

    @Inject
    EventDeserializer eventDeserializer;

    @Transactional
    public void saveAndPublish(ScreenAggregate aggregate) {
        List<BaseEvent> events = aggregate.getUncommittedChanges();
        for (BaseEvent event : events) {
            // Serialize the BaseEvent to JSON
            String payload = eventSerializer.serialize(event);

            // Persist the Event
            Event persistentEvent = new Event(event, payload, aggregate.getVersion());
            em.persist(persistentEvent);

            // Publish the Event
            screenEventPublisher.publish(event);
        }

        // Mark changes as committed
        aggregate.markChangesAsCommitted();
    }

    @Transactional
    public void saveAndPublish(ScriptAggregate aggregate) {
        List<BaseEvent> events = aggregate.getUncommittedChanges();
        for (BaseEvent event : events) {
            // Serialize the BaseEvent to JSON
            String payload = eventSerializer.serialize(event);

            // Persist the Event
            Event persistentEvent = new Event(event, payload, aggregate.getVersion());
            em.persist(persistentEvent);

            // Publish the Event
            scriptEventPublisher.publish(event);
        }

        // Mark changes as committed
        aggregate.markChangesAsCommitted();
    }

    // Method to load events for a given aggregate ID
    public List<BaseEvent> loadEventsForAggregate(UUID aggregateId) {
        // Query the Event table to get all events for the specified aggregate ID
        TypedQuery<Event> query = em.createQuery(
                "SELECT e FROM Event e WHERE e.aggregateId = :aggregateId ORDER BY e.version ASC",
                Event.class
        );
        query.setParameter("aggregateId", aggregateId);

        // Execute the query and get the result list
        List<Event> eventEntities = query.getResultList();

        // Deserialize each event from JSON to BaseEvent
        return eventEntities.stream()
                .map(eventEntity -> eventDeserializer.deserialize(eventEntity.getPayload()))
                .collect(Collectors.toList());
    }
}
