package org.master.eventsourcing;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.master.domain.AggregateRoot;
import org.master.domain.dataObject.DataObjectAggregate;
import org.master.domain.form.FormAggregate;
import org.master.domain.process.ProcessAggregate;
import org.master.domain.process.TaskAggregate;
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

    @Inject
    ScreenEventPublisher screenEventPublisher;

    @Inject
    ScriptEventPublisher scriptEventPublisher;

    @Inject
    FormEventPublisher formEventPublisher;

    @Inject
    DataObjectEventPublisher dataObjectEventPublisher;

    @Inject
    ProcessEventPublisher processEventPublisher;

    @Inject
    TaskEventPublisher taskEventPublisher;

    @Inject
    EventSerializer eventSerializer;

    @Inject
    EventDeserializer eventDeserializer;

    @Transactional
    public void saveAndPublish(ScreenAggregate aggregate) {
        saveAndPublish(aggregate, screenEventPublisher);
    }

    @Transactional
    public void saveAndPublish(ScriptAggregate aggregate) {
        saveAndPublish(aggregate, scriptEventPublisher);
    }

    @Transactional
    public void saveAndPublish(FormAggregate aggregate) {
        saveAndPublish(aggregate, formEventPublisher);
    }


    @Transactional
    public void saveAndPublish(DataObjectAggregate aggregate) {
        saveAndPublish(aggregate, dataObjectEventPublisher);
    }

    @Transactional
    public void saveAndPublish(ProcessAggregate aggregate) {
        saveAndPublish(aggregate, processEventPublisher);
    }

    @Transactional
    public void saveAndPublish(TaskAggregate aggregate) {
        saveAndPublish(aggregate, taskEventPublisher);
    }

    @Transactional
    public void saveAndPublish(AggregateRoot aggregate, EventPublisher eventPublisher) {
        List<BaseEvent> events = aggregate.getUncommittedChanges();
        for (BaseEvent event : events) {
            // Serialize the BaseEvent to JSON
            String payload = eventSerializer.serialize(event);

            // Persist the Event
            Event persistentEvent = new Event(event, payload, aggregate.getVersion());
            em.persist(persistentEvent);

            // Publish the Event
            eventPublisher.publish(event);
        }

        // Mark changes as committed and published
        aggregate.markChangesAsCommitted();
        aggregate.markChangesAsPublished();
    }

    @Transactional
    public void save(AggregateRoot aggregate){
        List<BaseEvent> events = aggregate.getUncommittedChanges();
        for (BaseEvent event : events) {
            // Serialize the BaseEvent to JSON
            String payload = eventSerializer.serialize(event);

            // Persist the Event
            Event persistentEvent = new Event(event, payload, aggregate.getVersion());
            em.persist(persistentEvent);
        }

        // Mark changes as committed
        aggregate.markChangesAsCommitted();
    }

    @Transactional
    public void publish(AggregateRoot aggregate, EventPublisher eventPublisher){
        List<BaseEvent> events = aggregate.getUnpublishedChanges();
        for (BaseEvent event : events) {
            // Serialize the BaseEvent to JSON
            String payload = eventSerializer.serialize(event);

            // Persist the Event
            Event persistentEvent = new Event(event, payload, aggregate.getVersion());
            em.persist(persistentEvent);

            // Publish the Event
            eventPublisher.publish(event);
        }

        // Mark changes as committed
        aggregate.markChangesAsPublished();
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
