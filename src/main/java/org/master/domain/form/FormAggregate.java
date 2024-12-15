package org.master.domain.form;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.master.command.form.commands.CreateFormCommand;
import org.master.command.form.commands.DeleteFormCommand;
import org.master.command.form.commands.PublishFormCommand;
import org.master.command.form.commands.UpdateFormCommand;
import org.master.domain.AggregateRoot;
import org.master.events.BaseEvent;
import org.master.events.form.FormCreatedEvent;
import org.master.events.form.FormDeletedEvent;
import org.master.events.form.FormPublishedEvent;
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
    private UUID dataObjectUUID;

    public FormAggregate(UUID id) {
        super(id);
    }

    // Factory method to create a new FormAggregate
    public static FormAggregate createForm(CreateFormCommand createFormCommand) {
        UUID id = UUID.randomUUID(); // Generate a new UUID for the aggregate
        FormAggregate aggregate = new FormAggregate(id);
        UUID dataObjectUUID = null;
        if (createFormCommand.dataObjectsWriteModel() != null) {
            dataObjectUUID = createFormCommand.dataObjectsWriteModel().getId();
        }
        // Creating the FormCreatedEvent with all necessary data
        FormCreatedEvent event = new FormCreatedEvent(
                id,
                createFormCommand.name(),
                createFormCommand.columns(),
                createFormCommand.rowHeights(),
                createFormCommand.primaryLanguageId(),
                createFormCommand.rowMaxHeights(),
                createFormCommand.columnMapping(),
                createFormCommand.definition(),
                dataObjectUUID
        );
        aggregate.apply(event);
        return aggregate;
    }

    // Factory method to create a new FormAggregate and UUID
    public static FormAggregate createForm(UUID id, CreateFormCommand createFormCommand) {
        FormAggregate aggregate = new FormAggregate(id);
        UUID dataObjectUUID = null;
        if (createFormCommand.dataObjectsWriteModel() != null) {
            dataObjectUUID = createFormCommand.dataObjectsWriteModel().getId();
        }
        // Creating the FormCreatedEvent with all necessary data
        FormCreatedEvent event = new FormCreatedEvent(
                id,
                createFormCommand.name(),
                createFormCommand.columns(),
                createFormCommand.rowHeights(),
                createFormCommand.primaryLanguageId(),
                createFormCommand.rowMaxHeights(),
                createFormCommand.columnMapping(),
                createFormCommand.definition(),
                dataObjectUUID
        );
        aggregate.apply(event);
        return aggregate;
    }

    public void updateForm(UpdateFormCommand updateFormCommand) {
        // Creating the FormUpdatedEvent with all necessary data
        UUID dataObjectUUID = null;
        if (updateFormCommand.dataObjectsWriteModel() != null) {
            dataObjectUUID = updateFormCommand.dataObjectsWriteModel().getId();
        }
        FormUpdatedEvent event = new FormUpdatedEvent(
                updateFormCommand.id(),
                updateFormCommand.columns(),
                updateFormCommand.rowHeights(),
                updateFormCommand.primaryLanguageId(),
                updateFormCommand.rowMaxHeights(),
                updateFormCommand.columnMapping(),
                updateFormCommand.definition(),
                dataObjectUUID
        );
        this.apply(event);
    }

    public void publishForm(PublishFormCommand publishFormCommand) {
        // Creating the FormPublishedEvent with all necessary data
        FormPublishedEvent event = new FormPublishedEvent(
                publishFormCommand.id(),
                this.getColumns(),
                this.getRowHeights(),
                this.getPrimaryLanguageId(),
                this.getRowMaxHeights(),
                this.getColumnMapping(),
                this.getDefinition()
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
                formCreatedEvent.getDefinition(),
                formCreatedEvent.getDataObjectUUID()
        );
    }

    private void handle(final FormUpdatedEvent formUpdatedEvent){
        setAggregateData(
                formUpdatedEvent.getColumnMapping(),
                formUpdatedEvent.getColumns(),
                formUpdatedEvent.getRowHeights(),
                formUpdatedEvent.getPrimaryLanguageId(),
                formUpdatedEvent.getRowMaxHeights(),
                formUpdatedEvent.getDefinition(),
                formUpdatedEvent.getDataObjectUUID());
    }

    private void handle(){
        super.markAsDeleted();
    }

    private void setAggregateData(JsonNode columnMapping, Integer columns, List<Integer> rowHeights,
                                  UUID primaryLanguageId, List<Integer> rowMaxHeights, JsonNode definition,
                                  UUID dataObjectUUID){
        this.columns = columns;
        this.rowHeights = rowHeights;
        this.primaryLanguageId = primaryLanguageId;
        this.rowMaxHeights = rowMaxHeights;
        this.columnMapping = columnMapping;
        this.definition = definition;
        this.dataObjectUUID = dataObjectUUID;
    }
}
