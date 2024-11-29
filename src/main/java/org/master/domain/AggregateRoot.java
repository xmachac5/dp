package org.master.domain;

import lombok.Getter;
import org.master.events.BaseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public abstract class AggregateRoot {

    @Getter
    private final UUID id; // Unique identifier for the aggregate
    @Getter
    private int version; // Current version of the aggregate
    private final List<BaseEvent> uncommittedChanges = new ArrayList<>(); // Uncommitted domain events

    protected AggregateRoot(UUID id) {
        this.id = id;
        this.version = 0;
    }

    /**
     * Apply an event to this aggregate, mutating its state.
     */
    public void apply(BaseEvent  event) {
        // Mutate the state
        this.when(event);

        // Track the event as an uncommitted change
        this.uncommittedChanges.add(event);
    }

    /**
     * Replay events to rebuild the state of the aggregate.
     */
    public void rehydrate(List<BaseEvent > eventStream) {
        for (BaseEvent  event : eventStream) {
            when(event); // Apply each event
            this.version++; // Increment version for each applied event
        }
    }

    /**
     * Retrieve uncommitted changes (newly generated events).
     */
    public List<BaseEvent > getUncommittedChanges() {
        return Collections.unmodifiableList(uncommittedChanges);
    }

    /**
     * Mark all changes as committed, clearing the uncommitted events.
     */
    public void markChangesAsCommitted() {
        uncommittedChanges.clear();
    }

    /**
     * Abstract method to handle events, to be implemented by concrete aggregates.
     */
    protected abstract void when(BaseEvent  event);
}

