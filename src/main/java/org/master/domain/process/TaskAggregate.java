package org.master.domain.process;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.master.command.process.task.commands.*;
import org.master.domain.AggregateRoot;
import org.master.events.BaseEvent;
import org.master.events.process.task.TaskCreatedEvent;
import org.master.events.process.task.TaskDeletedEvent;
import org.master.events.process.task.TaskUpdatedEvent;
import org.master.model.process.task.TaskType;

import java.util.UUID;

@Getter
@EqualsAndHashCode(callSuper = false)
public class TaskAggregate extends AggregateRoot {

    private String name;
    private TaskType type;
    private UUID targetEntityUuid;
    private JsonNode variableMapping;
    private UUID processWriteModel;
    private JsonNode columnsMapping;

    public TaskAggregate(UUID id) {
        super(id);
    }

    // Factory method to create a new TaskAggregate from DO task command
    public static TaskAggregate createTask(CreateDOTaskCommand createDOTaskCommand, UUID processWriteModel) {
        UUID id = UUID.randomUUID(); // Generate a new UUID for the aggregate
        TaskAggregate aggregate = new TaskAggregate(id);
        // Creating the TaskCreatedEvent with all necessary data
        TaskCreatedEvent event = new TaskCreatedEvent(
                id,
                createDOTaskCommand.name(),
                createDOTaskCommand.variableMapping(),
                TaskType.DO,
                createDOTaskCommand.dataObjectsWriteModel(),
                processWriteModel,
                createDOTaskCommand.columnsMapping()
        );
        aggregate.apply(event);
        return aggregate;
    }

    // Factory method to create a new TaskAggregate from DO task command
    public static TaskAggregate createTask(CreateScreenTaskCommand createScreenTaskCommand, UUID processWriteModel) {
        UUID id = UUID.randomUUID(); // Generate a new UUID for the aggregate
        TaskAggregate aggregate = new TaskAggregate(id);
        // Creating the TaskCreatedEvent with all necessary data
        TaskCreatedEvent event = new TaskCreatedEvent(
                id,
                createScreenTaskCommand.name(),
                createScreenTaskCommand.variableMapping(),
                TaskType.SCREEN,
                createScreenTaskCommand.screenWriteModel(),
                processWriteModel,
                null
        );
        aggregate.apply(event);
        return aggregate;
    }

    // Factory method to create a new TaskAggregate from DO task command
    public static TaskAggregate createTask(CreateScriptTaskCommand createScriptTaskCommand, UUID processWriteModel) {
        UUID id = UUID.randomUUID(); // Generate a new UUID for the aggregate
        TaskAggregate aggregate = new TaskAggregate(id);
        // Creating the TaskCreatedEvent with all necessary data
        TaskCreatedEvent event = new TaskCreatedEvent(
                id,
                createScriptTaskCommand.name(),
                createScriptTaskCommand.variableMapping(),
                TaskType.SCRIPT,
                createScriptTaskCommand.scriptWriteModel(),
                processWriteModel,
                null
        );
        aggregate.apply(event);
        return aggregate;
    }

    public void updateTask(UpdateDOTaskCommand updateDOTaskCommand) {
        // Creating the TaskUpdatedEvent with all necessary data
        TaskUpdatedEvent event = new TaskUpdatedEvent(
                updateDOTaskCommand.id(),
                updateDOTaskCommand.name(),
                updateDOTaskCommand.variableMapping(),
                updateDOTaskCommand.dataObjectsWriteModel(),
                updateDOTaskCommand.columnsMapping()
        );
        this.apply(event);
    }

    public void updateTask(UpdateScreenTaskCommand updateScreenTaskCommand) {
        // Creating the TaskUpdatedEvent with all necessary data
        TaskUpdatedEvent event = new TaskUpdatedEvent(
                updateScreenTaskCommand.id(),
                updateScreenTaskCommand.name(),
                updateScreenTaskCommand.variableMapping(),
                updateScreenTaskCommand.screenWriteModel(),
                null
        );
        this.apply(event);
    }

    public void updateTask(UpdateScriptTaskCommand updateScriptTaskCommand) {
        // Creating the TaskUpdatedEvent with all necessary data
        TaskUpdatedEvent event = new TaskUpdatedEvent(
                updateScriptTaskCommand.id(),
                updateScriptTaskCommand.name(),
                updateScriptTaskCommand.variableMapping(),
                updateScriptTaskCommand.scriptWriteModel(),
                null
        );
        this.apply(event);
    }

    public void deleteTask(DeleteTaskCommand deleteTaskCommand) {
        // Creating the TaskDeletedEvent with DO task id
        TaskDeletedEvent event = new TaskDeletedEvent(deleteTaskCommand.uuid());
        this.apply(event);
    }

    @Override
    protected void when(BaseEvent event) {
        if (event instanceof TaskCreatedEvent created) {
            handle(created);
        } else if (event instanceof TaskUpdatedEvent updated) {
            handle(updated);
        } else if (event instanceof TaskDeletedEvent) {
            handle();
        }
    }

    private void handle(final TaskCreatedEvent taskCreatedEvent){
        this.name = taskCreatedEvent.getName();
        this.columnsMapping = taskCreatedEvent.getColumnsMapping();
        this.type = taskCreatedEvent.getType();
        this.variableMapping = taskCreatedEvent.getColumnsMapping();
        this.targetEntityUuid = taskCreatedEvent.getTargetEntityUuid();
        this.processWriteModel = taskCreatedEvent.getProcessWriteModel();
    }

    private void handle(final TaskUpdatedEvent taskUpdatedEvent){
        this.name = taskUpdatedEvent.getName();
        this.variableMapping = taskUpdatedEvent.getVariableMapping();
        this.columnsMapping = taskUpdatedEvent.getColumnsMapping();
        this.targetEntityUuid = taskUpdatedEvent.getTargetEntityUuid();

    }

    private void handle(){
        super.markAsDeleted();
    }

}
