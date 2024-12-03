package org.master.domain.script;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.master.command.script.commands.CreateScriptCommand;
import org.master.command.script.commands.DeleteScriptCommand;
import org.master.command.script.commands.UpdateScriptCommand;
import org.master.domain.AggregateRoot;
import org.master.events.BaseEvent;
import org.master.events.script.ScriptCreatedEvent;
import org.master.events.script.ScriptDeletedEvent;
import org.master.events.script.ScriptUpdatedEvent;

import java.util.UUID;

@Getter
@EqualsAndHashCode(callSuper = false)
public class ScriptAggregate extends AggregateRoot {

    private JsonNode variables;
    private String name;
    private String code;

    public ScriptAggregate(UUID id) {
        super(id);
    }

    // Factory method to create a new ScriptAggregate
    public static ScriptAggregate createScript(CreateScriptCommand createScriptCommand) {
        UUID id = UUID.randomUUID(); // Generate a new UUID for the aggregate
        ScriptAggregate aggregate = new ScriptAggregate(id);
        // Creating the ScriptCreatedEvent with all necessary data
        ScriptCreatedEvent event = new ScriptCreatedEvent(
                id,
                createScriptCommand.name(),
                createScriptCommand.variables(),
                createScriptCommand.code()
        );
        aggregate.apply(event);
        return aggregate;
    }

    public void updateScript(UpdateScriptCommand updateScriptCommand) {
        // Creating the ScriptUpdatedEvent with all necessary data
        ScriptUpdatedEvent event = new ScriptUpdatedEvent(updateScriptCommand.id(), updateScriptCommand.name(),
                updateScriptCommand.variables(), updateScriptCommand.code());
        this.apply(event);
    }

    public void deleteScript(DeleteScriptCommand deleteScriptCommand) {
        // Creating the ScriptDeletedEvent with screen id
        ScriptDeletedEvent event = new ScriptDeletedEvent(deleteScriptCommand.id());
        this.apply(event);
    }

    @Override
    protected void when(BaseEvent event) {
        if (event instanceof ScriptCreatedEvent created) {
            handle(created);
        } else if (event instanceof ScriptUpdatedEvent updated) {
            handle(updated);
        } else if (event instanceof ScriptDeletedEvent) {
            handle();
        }
    }

    private void handle(final ScriptCreatedEvent scriptCreatedEvent){
        setAggregateData(scriptCreatedEvent.getName(), scriptCreatedEvent.getVariables(), scriptCreatedEvent.getCode());
    }

    private void handle(final ScriptUpdatedEvent scriptUpdatedEvent){
        setAggregateData(scriptUpdatedEvent.getName(), scriptUpdatedEvent.getVariables(), scriptUpdatedEvent.getCode());
    }

    private void handle(){
        super.markAsDeleted();
    }

    private void setAggregateData(String name, JsonNode variables, String code) {
        this.name = name;
        this.variables = variables;
        this.code = code;
    }
}
