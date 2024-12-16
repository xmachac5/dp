package org.master.command.script.handler;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.master.command.script.commands.CreateScriptCommand;
import org.master.command.script.commands.DeleteScriptCommand;
import org.master.command.script.commands.PublishScriptCommand;
import org.master.command.script.commands.UpdateScriptCommand;
import org.master.domain.script.ScriptAggregate;
import org.master.events.BaseEvent;
import org.master.eventsourcing.EventStore;
import org.master.repository.script.ScriptWriteRepository;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class ScriptCommandHandler {
    @Inject
    EventStore eventStore;

    @Inject
    ScriptWriteRepository scriptWriteRepository;


    @Transactional
    public UUID handle(CreateScriptCommand command) {

        // Use factory method to create a new aggregate
        ScriptAggregate aggregate = ScriptAggregate.createScript(command);

        // Persist and publish events
        eventStore.saveAndPublish(aggregate);

        scriptWriteRepository.create(aggregate.getId(), command);

        return aggregate.getId();

    }

    @Transactional
    public void handle(UpdateScriptCommand command) {

        ScriptAggregate aggregate = rehydrate(command.id(), "Cannot update deleted Script");

        // Apply the update command
        aggregate.updateScript(command);

        // Persist and publish events
        eventStore.saveAndPublish(aggregate);

        // Update the write model in the repository
        scriptWriteRepository.update(command);
    }

    @Transactional
    public void handle(PublishScriptCommand command) {

        ScriptAggregate aggregate = rehydrate(command.id(), "Cannot update deleted Script");

        // Apply the update command
        aggregate.publishScript(command);

        // Persist and publish events
        eventStore.saveAndPublish(aggregate);
    }

    @Transactional
    public void handle(DeleteScriptCommand command) {

        ScriptAggregate aggregate = rehydrate(command.id(), "Cannot delete deleted Script");

        // Apply the update command
        aggregate.deleteScript(command);

        // Persist and publish events
        eventStore.saveAndPublish(aggregate);

        // Update the write model in the repository
        scriptWriteRepository.delete(command);

    }

    private ScriptAggregate rehydrate(UUID uuid, String errorText){

        List<BaseEvent> eventStream = eventStore.loadEventsForAggregate(uuid);

        if (eventStream.isEmpty()) {
            throw new IllegalArgumentException("Form not found with ID: " + uuid);
        }

        // Create the aggregate and rehydrate it
        ScriptAggregate aggregate = new ScriptAggregate(uuid);
        aggregate.rehydrate(eventStream);

        if (aggregate.isDeleted()) {
            throw new IllegalStateException(errorText);
        }
        return aggregate;
    }
}
