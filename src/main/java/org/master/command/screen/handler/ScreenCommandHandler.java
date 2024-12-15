package org.master.command.screen.handler;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.master.command.screen.commands.CreateScreenCommand;
import org.master.command.screen.commands.DeleteScreenCommand;
import org.master.command.screen.commands.PublishScreenCommands;
import org.master.command.screen.commands.UpdateScreenCommand;
import org.master.domain.screen.ScreenAggregate;
import org.master.events.BaseEvent;
import org.master.eventsourcing.EventStore;
import org.master.repository.screen.ScreenWriteRepository;
import java.util.List;
import java.util.UUID;

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

        ScreenAggregate aggregate = rehydrate(command.id(), "Cannot update deleted Screen");

        // Apply the update command
        aggregate.updateScreen(command);

        // Persist and publish events
        eventStore.saveAndPublish(aggregate);

        // Update the write model in the repository
        screenWriteRepository.update(command);
    }

    @Transactional
    public void handle(PublishScreenCommands command) {

        ScreenAggregate aggregate = rehydrate(command.id(), "Cannot update deleted Screen");

        // Apply the update command
        aggregate.publishScreen(command);

        // Persist and publish events
        eventStore.saveAndPublish(aggregate);
    }

    @Transactional
    public void handle(DeleteScreenCommand command) {

        ScreenAggregate aggregate = rehydrate(command.uuid(), "Cannot delete deleted Screen");

        // Apply the update command
        aggregate.deleteScreen(command);

        // Persist and publish events
        eventStore.saveAndPublish(aggregate);

        // Update the write model in the repository
        screenWriteRepository.delete(command);
    }

    private ScreenAggregate rehydrate(UUID uuid, String errorText){

        List<BaseEvent> eventStream = eventStore.loadEventsForAggregate(uuid);

        if (eventStream.isEmpty()) {
            throw new IllegalArgumentException("Form not found with ID: " + uuid);
        }

        // Create the aggregate and rehydrate it
        ScreenAggregate aggregate = new ScreenAggregate(uuid);
        aggregate.rehydrate(eventStream);

        if (aggregate.isDeleted()) {
            throw new IllegalStateException(errorText);
        }
        return aggregate;
    }
}
