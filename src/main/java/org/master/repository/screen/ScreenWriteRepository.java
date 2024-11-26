package org.master.repository.screen;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.master.domain.screen.ScreenAggregate;
import org.master.events.BaseEvent;
import org.master.events.screen.ScreenEventDeserializer;
import org.master.model.Event;
import org.master.model.screen.ScreenWriteModel;

import java.util.List;
import java.util.UUID;


@ApplicationScoped
public class ScreenWriteRepository implements PanacheRepository<ScreenWriteModel> {

    @Inject
    EntityManager em;

    @Inject
    ScreenEventDeserializer screenEventDeserializer;

    public ScreenAggregate load(UUID aggregateId) {
        // Fetch events from the database
        List<Event> storedEvents = em.createQuery("SELECT e FROM Event e WHERE e.aggregateId = :id ORDER BY e.timestamp ASC", Event.class)
                .setParameter("id", aggregateId)
                .getResultList();

        if (storedEvents.isEmpty()) {
            throw new IllegalStateException("No events found for aggregate ID: " + aggregateId);
        }

        // Rehydrate the ScreenAggregate
        ScreenAggregate aggregate = new ScreenAggregate(aggregateId);
        for (Event storedEvent : storedEvents) {
            // Deserialize payload into a BaseEvent or its subclass
            BaseEvent event = screenEventDeserializer.deserialize(storedEvent.getPayload());
            aggregate.apply(event); // Apply the event to rebuild state
        }
        return aggregate;
    }
}
