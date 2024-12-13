package org.master.command.process.task.handler;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.master.command.process.task.commands.*;
import org.master.domain.process.TaskAggregate;
import org.master.events.BaseEvent;
import org.master.eventsourcing.EventStore;
import org.master.model.process.ProcessWriteModel;
import org.master.repository.process.ProcessWriteRepository;
import org.master.repository.process.TaskWriteRepository;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class TaskCommandHandler {
    @Inject
    EventStore eventStore;

    @Inject
    TaskWriteRepository taskWriteRepository;

    @Inject
    ProcessWriteRepository processWriteRepository;

    @Transactional
    public void handle(CreateDOTaskCommand command) {

        //Find process write model by version uuid
        ProcessWriteModel processWriteModel = processWriteRepository.findByVersionUuid(command.processVersionWriteModel());

        // Use factory method to create a new aggregate
        TaskAggregate aggregate = TaskAggregate.createTask(command, processWriteModel.getId());

        // Persist and publish events
        eventStore.saveAndPublish(aggregate);

        taskWriteRepository.create(aggregate.getId(), command);

    }

    @Transactional
    public void handle(CreateScreenTaskCommand command) {

        //Find process write model by version uuid
        ProcessWriteModel processWriteModel = processWriteRepository.findByVersionUuid(command.processVersionWriteModel());

        // Use factory method to create a new aggregate
        TaskAggregate aggregate = TaskAggregate.createTask(command, processWriteModel.getId());

        // Persist and publish events
        eventStore.saveAndPublish(aggregate);

        taskWriteRepository.create(aggregate.getId(), command);

    }

    @Transactional
    public void handle(CreateScriptTaskCommand command) {

        //Find process write model by version uuid
        ProcessWriteModel processWriteModel = processWriteRepository.findByVersionUuid(command.processVersionWriteModel());

        // Use factory method to create a new aggregate
        TaskAggregate aggregate = TaskAggregate.createTask(command, processWriteModel.getId());

        // Persist and publish events
        eventStore.saveAndPublish(aggregate);

        taskWriteRepository.create(aggregate.getId(), command);

    }

    @Transactional
    public void handle(UpdateDOTaskCommand command) {

        TaskAggregate aggregate = rehydrate(command.id(), "Cannot update deleted Task");

        // Apply the update command
        aggregate.updateTask(command);

        // Persist and publish events
        eventStore.saveAndPublish(aggregate);

        // Update the write model in the repository
        taskWriteRepository.update(command);
    }

    @Transactional
    public void handle(UpdateScreenTaskCommand command) {

        TaskAggregate aggregate = rehydrate(command.id(), "Cannot update deleted Task");

        // Apply the update command
        aggregate.updateTask(command);

        // Persist and publish events
        eventStore.saveAndPublish(aggregate);

        // Update the write model in the repository
        taskWriteRepository.update(command);
    }

    @Transactional
    public void handle(UpdateScriptTaskCommand command) {

        TaskAggregate aggregate = rehydrate(command.id(), "Cannot update deleted Task");

        // Apply the update command
        aggregate.updateTask(command);

        // Persist and publish events
        eventStore.saveAndPublish(aggregate);

        // Update the write model in the repository
        taskWriteRepository.update(command);
    }

    @Transactional
    public void handle(DeleteTaskCommand command) {

        TaskAggregate aggregate = rehydrate(command.uuid(), "Cannot delete deleted Task");

        // Apply the update command
        aggregate.deleteTask(command);

        // Persist and publish events
        eventStore.saveAndPublish(aggregate);

        // Update the write model in the repository
        switch (aggregate.getType()) {
            case DO -> taskWriteRepository.delete(new DeleteDOTaskCommand(command.uuid()));
            case SCREEN -> taskWriteRepository.delete(new DeleteScreenTaskCommand(command.uuid()));
            case SCRIPT -> taskWriteRepository.delete(new DeleteScriptTaskCommand(command.uuid()));
        }


    }

    private TaskAggregate rehydrate(UUID uuid, String errorText){

        List<BaseEvent> eventStream = eventStore.loadEventsForAggregate(uuid);

        if (eventStream.isEmpty()) {
            throw new IllegalArgumentException("Task not found with ID: " + uuid);
        }

        // Create the aggregate and rehydrate it
        TaskAggregate aggregate = new TaskAggregate(uuid);
        aggregate.rehydrate(eventStream);

        if (aggregate.isDeleted()) {
            throw new IllegalStateException(errorText);
        }
        return aggregate;
    }
}
