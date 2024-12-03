package org.master.command.screen.handler;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.master.command.screen.commands.CreateScreenCommand;
import org.master.command.screen.commands.DeleteScreenCommand;
import org.master.command.screen.commands.UpdateScreenCommand;
import org.master.domain.screen.ScreenAggregate;
import org.master.events.BaseEvent;
import org.master.eventsourcing.EventStore;
import org.master.model.screen.ScreenWriteModel;
import org.master.repository.screen.ScreenWriteRepository;
import java.util.List;

@ApplicationScoped
public class ScreenCommandHandler {

    @Inject
    EventStore eventStore;

    @Inject
    ScreenWriteRepository screenWriteRepository;


    @Transactional
    public void handle(CreateScreenCommand command) {

        // Use factory method to create a new aggregate
        ScreenAggregate aggregate = ScreenAggregate.createScreen(command);

        // Persist and publish events
        eventStore.saveAndPublish(aggregate);

        screenWriteRepository.create(aggregate.getId(), command);

    }

    @Transactional
    public void handle(UpdateScreenCommand command) {
        ScreenWriteModel screenWriteModel = screenWriteRepository.findByUuid(command.id());

        if(screenWriteModel != null) {
            // Load existing aggregate by rehydrating it from event store
            List< BaseEvent > eventStream = eventStore.loadEventsForAggregate(command.id());

            if (eventStream.isEmpty()) {
                throw new IllegalArgumentException("Screen not found with ID: " + command.id());
            }

            // Create the aggregate and rehydrate it
            ScreenAggregate aggregate = new ScreenAggregate(command.id());
            aggregate.rehydrate(eventStream);

            if (aggregate.isDeleted()) {
                throw new IllegalStateException("Cannot update a deleted screen");
            }

            // Apply the update command
            aggregate.updateScreen(command);

            // Persist and publish events
            eventStore.saveAndPublish(aggregate);

            // Update the write model in the repository
            screenWriteRepository.update(command);

        }else {
            throw new IllegalArgumentException("Screen not found with ID: " + command.id());
        }
    }

    @Transactional
    public void handle(DeleteScreenCommand command) {
        ScreenWriteModel screenWriteModel = screenWriteRepository.findByUuid(command.uuid());

        if(screenWriteModel != null) {
            // Load existing aggregate by rehydrating it from event store
            List< BaseEvent > eventStream = eventStore.loadEventsForAggregate(command.uuid());

            if (eventStream.isEmpty()) {
                throw new IllegalArgumentException("Screen not found with ID: " + command.uuid());
            }

            // Create the aggregate and rehydrate it
            ScreenAggregate aggregate = new ScreenAggregate(command.uuid());
            aggregate.rehydrate(eventStream);

            if (aggregate.isDeleted()) {
                throw new IllegalStateException("Cannot update a deleted screen");
            }

            // Apply the update command
            aggregate.deleteScreen(command);

            // Persist and publish events
            eventStore.saveAndPublish(aggregate);
            // Update the write model in the repository
            screenWriteRepository.delete(command);

        }else {
            throw new IllegalArgumentException("Screen not found with ID: " + command.uuid());
        }

    }
}
