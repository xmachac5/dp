package org.master.domain.form;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.master.command.form.commands.CreateFormCommand;
import org.master.command.form.commands.DeleteFormCommand;
import org.master.command.form.commands.UpdateFormCommand;
import org.master.domain.AggregateRoot;
import org.master.events.BaseEvent;
import org.master.events.form.FormCreatedEvent;
import org.master.events.form.FormDeletedEvent;
import org.master.events.form.FormUpdatedEvent;

import java.util.List;
import java.util.UUID;

@Getter
@EqualsAndHashCode(callSuper = false)
public class FormAggregate extends AggregateRoot {

    private String name;
    private Integer columns;
    private List<Integer> rowHeights;
    private UUID primaryLanguageId;
    private List<Integer> rowMaxHeights;
    private JsonNode columnMapping;
    private JsonNode definition;

    public FormAggregate(UUID id) {
        super(id);
    }

    // Factory method to create a new FormAggregate
    public static FormAggregate createForm(CreateFormCommand createFormCommand) {
        UUID id = UUID.randomUUID(); // Generate a new UUID for the aggregate
        FormAggregate aggregate = new FormAggregate(id);
        // Creating the FormCreatedEvent with all necessary data
        FormCreatedEvent event = new FormCreatedEvent(
                id,
                createFormCommand.name(),
                createFormCommand.columns(),
                createFormCommand.rowHeights(),
                createFormCommand.primaryLanguageId(),
                createFormCommand.rowMaxHeights(),
                createFormCommand.columnMapping(),
                createFormCommand.definition()
        );
        aggregate.apply(event);
        return aggregate;
    }

    public void updateForm(UpdateFormCommand updateFormCommand) {
        // Creating the FormUpdatedEvent with all necessary data
        FormUpdatedEvent event = new FormUpdatedEvent(
                updateFormCommand.id(),
                updateFormCommand.columns(),
                updateFormCommand.rowHeights(),
                updateFormCommand.primaryLanguageId(),
                updateFormCommand.rowMaxHeights(),
                updateFormCommand.columnMapping(),
                updateFormCommand.definition()
        );
        this.apply(event);
    }

    public void deleteForm(DeleteFormCommand deleteFormCommand) {
        // Creating the FormDeletedEvent with screen id
        FormDeletedEvent event = new FormDeletedEvent(deleteFormCommand.uuid());
        this.apply(event);
    }

    @Override
    protected void when(BaseEvent event) {
        if (event instanceof FormCreatedEvent created) {
            handle(created);
        } else if (event instanceof FormUpdatedEvent updated) {
            handle(updated);
        } else if (event instanceof FormDeletedEvent) {
            handle();
        }
    }

    private void handle(final FormCreatedEvent formCreatedEvent){
        this.name = formCreatedEvent.getName();
        setAggregateData(
                formCreatedEvent.getColumnMapping(),
                formCreatedEvent.getColumns(),
                formCreatedEvent.getRowHeights(),
                formCreatedEvent.getPrimaryLanguageId(),
                formCreatedEvent.getRowMaxHeights(),
                formCreatedEvent.getDefinition());
    }

    private void handle(final FormUpdatedEvent formUpdatedEvent){
        setAggregateData(
                formUpdatedEvent.getColumnMapping(),
                formUpdatedEvent.getColumns(),
                formUpdatedEvent.getRowHeights(),
                formUpdatedEvent.getPrimaryLanguageId(),
                formUpdatedEvent.getRowMaxHeights(),
                formUpdatedEvent.getDefinition());
    }

    private void handle(){
        super.markAsDeleted();
    }

    private void setAggregateData(JsonNode columnMapping, Integer columns, List<Integer> rowHeights,
                                  UUID primaryLanguageId, List<Integer> rowMaxHeights, JsonNode definition){
        this.columns = columns;
        this.rowHeights = rowHeights;
        this.primaryLanguageId = primaryLanguageId;
        this.rowMaxHeights = rowMaxHeights;
        this.columnMapping = columnMapping;
        this.definition = definition;
    }
}
