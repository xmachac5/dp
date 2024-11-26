package org.master.command.screen.handler;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.master.command.screen.commands.CreateScreenCommand;
import org.master.domain.screen.ScreenAggregate;
import org.master.eventsourcing.EventStore;

@ApplicationScoped
public class ScreenCommandHandler {

    @Inject
    EventStore eventStore;


    @Transactional
    public void handle(CreateScreenCommand command) {
        // Use factory method to create a new aggregate
        ScreenAggregate aggregate = ScreenAggregate.createScreen(command.getScreenCreateDTO());

        // Persist and publish events
        eventStore.saveAndPublish(aggregate);
    }
}
