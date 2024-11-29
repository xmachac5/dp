package org.master.command.screen.handler;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.master.command.screen.commands.CreateScreenCommand;
import org.master.domain.screen.ScreenAggregate;
import org.master.eventsourcing.EventStore;
import org.master.model.screen.ScreenWriteModel;
import org.master.repository.screen.ScreenWriteRepository;

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
}
