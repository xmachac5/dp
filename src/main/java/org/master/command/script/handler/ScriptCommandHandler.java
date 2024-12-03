package org.master.command.script.handler;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.master.command.script.commands.CreateScriptCommand;
import org.master.command.script.commands.DeleteScriptCommand;
import org.master.command.script.commands.UpdateScriptCommand;
import org.master.domain.script.ScriptAggregate;
import org.master.events.BaseEvent;
import org.master.eventsourcing.EventStore;
import org.master.model.script.ScriptWriteModel;
import org.master.repository.script.ScriptWriteRepository;

import java.util.List;

@ApplicationScoped
public class ScriptCommandHandler {
    @Inject
    EventStore eventStore;

    @Inject
    ScriptWriteRepository scriptWriteRepository;


    @Transactional
    public void handle(CreateScriptCommand command) {

        // Use factory method to create a new aggregate
        ScriptAggregate aggregate = ScriptAggregate.createScript(command);

        // Persist and publish events
        eventStore.saveAndPublish(aggregate);

        scriptWriteRepository.create(aggregate.getId(), command);

    }

    @Transactional
    public void handle(UpdateScriptCommand command) {
        ScriptWriteModel scriptWriteModel = scriptWriteRepository.findByUuid(command.id());

        if(scriptWriteModel != null) {
            // Load existing aggregate by rehydrating it from event store
            List<BaseEvent> eventStream = eventStore.loadEventsForAggregate(command.id());

            if (eventStream.isEmpty()) {
                throw new IllegalArgumentException("Script not found with ID: " + command.id());
            }

            // Create the aggregate and rehydrate it
            ScriptAggregate aggregate = new ScriptAggregate(command.id());
            aggregate.rehydrate(eventStream);

            if (aggregate.isDeleted()) {
                throw new IllegalStateException("Cannot update a deleted script");
            }

            // Apply the update command
            aggregate.updateScript(command);

            // Persist and publish events
            eventStore.saveAndPublish(aggregate);

            // Update the write model in the repository
            scriptWriteRepository.update(command);

        }else {
            throw new IllegalArgumentException("Script not found with ID: " + command.id());
        }
    }

    @Transactional
    public void handle(DeleteScriptCommand command) {
        ScriptWriteModel scriptWriteModel = scriptWriteRepository.findByUuid(command.id());

        if(scriptWriteModel != null) {
            // Load existing aggregate by rehydrating it from event store
            List< BaseEvent > eventStream = eventStore.loadEventsForAggregate(command.id());

            if (eventStream.isEmpty()) {
                throw new IllegalArgumentException("Script not found with ID: " + command.id());
            }

            // Create the aggregate and rehydrate it
            ScriptAggregate aggregate = new ScriptAggregate(command.id());
            aggregate.rehydrate(eventStream);

            if (aggregate.isDeleted()) {
                throw new IllegalStateException("Cannot update a deleted script");
            }

            // Apply the update command
            aggregate.deleteScript(command);

            // Persist and publish events
            eventStore.saveAndPublish(aggregate);
            // Update the write model in the repository
            scriptWriteRepository.delete(command);

        }else {
            throw new IllegalArgumentException("Script not found with ID: " + command.id());
        }

    }
}
