package org.master.domain.dataObject;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.master.command.dataObject.commands.CreateDataObjectCommand;
import org.master.command.dataObject.commands.DeleteDataObjectCommand;
import org.master.command.dataObject.commands.UpdateDataObjectCommand;
import org.master.domain.AggregateRoot;
import org.master.events.BaseEvent;
import org.master.events.dataObject.DataObjectCreatedEvent;
import org.master.events.dataObject.DataObjectDeletedEvent;
import org.master.events.dataObject.DataObjectUpdatedEvent;

import java.util.UUID;

@Getter
@EqualsAndHashCode(callSuper = false)
public class DataObjectAggregate extends AggregateRoot {

    private String name;
    private String description;
    private Boolean trackChanges;
    private Boolean softDelete;
    private JsonNode columns;

    public DataObjectAggregate(UUID id) {
        super(id);
    }

    // Factory method to create a new DataObject Aggregate
    public static DataObjectAggregate createDataObject(CreateDataObjectCommand createDataObjectCommand) {
        UUID id = UUID.randomUUID(); // Generate a new UUID for the aggregate
        DataObjectAggregate aggregate = new DataObjectAggregate(id);
        // Creating the DataObjectCreatedEvent with all necessary data
        DataObjectCreatedEvent event = new DataObjectCreatedEvent(
                id,
                createDataObjectCommand.name(),
                createDataObjectCommand.description(),
                createDataObjectCommand.trackChanges(),
                createDataObjectCommand.softDelete(),
                createDataObjectCommand.columns()
        );
        aggregate.apply(event);
        return aggregate;
    }

    public void updateDataObject(UpdateDataObjectCommand updateDataObjectCommand) {
        // Creating the DataObjectUpdatedEvent with all necessary data
        DataObjectUpdatedEvent event = new DataObjectUpdatedEvent(
                updateDataObjectCommand.id(),
                updateDataObjectCommand.description(),
                updateDataObjectCommand.trackChanges(),
                updateDataObjectCommand.softDelete(),
                updateDataObjectCommand.columns()
        );
        this.apply(event);
    }

    public void deleteDataObject(DeleteDataObjectCommand deleteDataObjectCommand) {
        // Creating the DataObjectDeletedEvent with screen id
        DataObjectDeletedEvent event = new DataObjectDeletedEvent(deleteDataObjectCommand.uuid());
        this.apply(event);
    }

    @Override
    protected void when(BaseEvent event) {
        if (event instanceof DataObjectCreatedEvent created) {
            handle(created);
        } else if (event instanceof DataObjectUpdatedEvent updated) {
            handle(updated);
        } else if (event instanceof DataObjectDeletedEvent) {
            handle();
        }
    }

    private void handle(final DataObjectCreatedEvent dataObjectCreatedEvent){
        this.name = dataObjectCreatedEvent.getName();
        setAggregateData(
                dataObjectCreatedEvent.getDescription(),
                dataObjectCreatedEvent.getColumns(),
                dataObjectCreatedEvent.getTrackChanges(),
                dataObjectCreatedEvent.getSoftDelete()
        );
    }

    private void handle(final DataObjectUpdatedEvent dataObjectUpdatedEvent){
        setAggregateData(
                dataObjectUpdatedEvent.getDescription(),
                dataObjectUpdatedEvent.getColumns(),
                dataObjectUpdatedEvent.getTrackChanges(),
                dataObjectUpdatedEvent.getSoftDelete()
        );
    }

    private void handle(){
        super.markAsDeleted();
    }

    private void setAggregateData(String description, JsonNode columns, Boolean trackChanges, Boolean softDelete) {
        this.columns = columns;
        this.description = description;
        this.trackChanges = trackChanges;
        this.softDelete = softDelete;
    }
}
