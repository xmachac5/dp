package org.master.command.screen.handler;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.master.command.screen.commands.CreateScreenCommand;
import org.master.domain.screen.ScreenAggregate;
import org.master.eventsourcing.EventStore;
import org.master.service.screen.ScreenService;

import java.util.UUID;

@ApplicationScoped
public class ScreenCommandHandler {

    @Inject
    EventStore eventStore;


    public void handle(CreateScreenCommand createScreenCommand){

        ScreenAggregate aggregate = new ScreenAggregate(UUID.randomUUID());
        ScreenAggregate.createScreen(createScreenCommand.getScreenCreateDTO());
        eventStore.saveAndPublish(aggregate); // Save and publish events

    }
}
