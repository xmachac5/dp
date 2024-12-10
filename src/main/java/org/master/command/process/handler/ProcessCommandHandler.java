package org.master.command.process.handler;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.master.command.process.commands.*;
import org.master.domain.process.ProcessAggregate;
import org.master.events.BaseEvent;
import org.master.eventsourcing.EventStore;
import org.master.repository.process.ProcessWriteRepository;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class ProcessCommandHandler {

    @Inject
    EventStore eventStore;

    @Inject
    ProcessWriteRepository processWriteRepository;


    @Transactional
    public void handle(CreateProcessCommand command) {

        // Use factory method to create a new aggregate
        ProcessAggregate aggregate = ProcessAggregate.createProcess(command);

        // Persist and publish events
        eventStore.saveAndPublish(aggregate);

        processWriteRepository.create(aggregate.getId(), command);

    }

    @Transactional
    public void handle(UpdateProcessCommand command) {

        ProcessAggregate aggregate = rehydrateProcess(command.id(), "Cannot update deleted Process");

        if (aggregate.getPublished()) {
            throw new IllegalStateException("Can not update published process.");
        }

        // Apply the update command
        aggregate.updateProcess(command);

        // Persist and publish events
        eventStore.saveAndPublish(aggregate);

        // Update the write model in the repository
        processWriteRepository.update(command);
    }

    @Transactional
    public void handle(DeleteProcessCommand command) {

        ProcessAggregate aggregate = rehydrateProcess(command.uuid(), "Cannot delete deleted Process");

        // Apply the update command
        aggregate.deleteProcess(command);

        // Persist and publish events
        eventStore.saveAndPublish(aggregate);

        // Update the write model in the repository
        processWriteRepository.delete(command);

    }

    @Transactional
    public void handle(ConceptProcessCommand command) {
        ProcessAggregate aggregate = rehydrateProcess(command.uuid(), "Cannot delete deleted Form Handler");

        if (!aggregate.getPublished()) {
            throw new IllegalStateException("Only published processes can be reverted to concept.");
        }

        // Apply the update command
        aggregate.conceptProcess(command);

        // Persist and publish events
        eventStore.saveAndPublish(aggregate);

        processWriteRepository.concept(command);

    }

    @Transactional
    public void handle(PublishProcessCommand command) {

        ProcessAggregate aggregate = rehydrateProcess(command.uuid(), "Cannot delete deleted Form Handler");

        if (!aggregate.getPublished()) {
            throw new IllegalStateException("Can not publish published process.");
        }

        // Apply the update command
        aggregate.publishProcess(command);

        // Persist and publish events
        eventStore.saveAndPublish(aggregate);

        // Update the write model in the repository
        processWriteRepository.publish(command);

    }

    private ProcessAggregate rehydrateProcess(UUID uuid, String errorText){

        List<BaseEvent> eventStream = eventStore.loadEventsForAggregate(uuid);

        if (eventStream.isEmpty()) {
            throw new IllegalArgumentException("Process not found with ID: " + uuid);
        }

        // Create the aggregate and rehydrate it
        ProcessAggregate aggregate = new ProcessAggregate(uuid);
        aggregate.rehydrate(eventStream);

        if (aggregate.isDeleted()) {
            throw new IllegalStateException(errorText);
        }
        return aggregate;
    }
}
