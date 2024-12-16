package org.master.command.dataObject.handler;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.master.command.dataObject.commands.CreateDataObjectCommand;
import org.master.command.dataObject.commands.DeleteDataObjectCommand;
import org.master.command.dataObject.commands.PublishDataObjectCommand;
import org.master.command.dataObject.commands.UpdateDataObjectCommand;
import org.master.domain.dataObject.DataObjectAggregate;
import org.master.events.BaseEvent;
import org.master.eventsourcing.EventStore;
import org.master.repository.dataObject.DataObjectWriteRepository;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class DataObjectCommandHandler {
    @Inject
    EventStore eventStore;

    @Inject
    DataObjectWriteRepository dataObjectWriteRepository;


    @Transactional
    public UUID handle(CreateDataObjectCommand command) {

        // Use factory method to create a new aggregate
        DataObjectAggregate aggregate = DataObjectAggregate.createDataObject(command);

        // Persist and publish events
        eventStore.saveAndPublish(aggregate);

        dataObjectWriteRepository.create(aggregate.getId(), command, 1);

        return aggregate.getId();
    }

    @Transactional
    public void handle(UpdateDataObjectCommand command) {

        DataObjectAggregate aggregate = rehydrate(command.id(), "Cannot update deleted DataObject");

        // Apply the update command
        aggregate.updateDataObject(command);

        // Persist and publish events
        eventStore.saveAndPublish(aggregate);

        // Update the write model in the repository
        dataObjectWriteRepository.update(command);

    }

    @Transactional
    public void handle(PublishDataObjectCommand command) {

        DataObjectAggregate aggregate = rehydrate(command.id(), "Cannot update deleted DataObject");

        // Apply the update command
        aggregate.publishDataObject(command);

        // Persist and publish events
        eventStore.saveAndPublish(aggregate);

    }

    @Transactional
    public void handle(DeleteDataObjectCommand command) {

        DataObjectAggregate aggregate = rehydrate(command.uuid(), "Cannot delete deleted DataObject");

        // Apply the update command
        aggregate.deleteDataObject(command);

        // Persist and publish events
        eventStore.saveAndPublish(aggregate);

        // Update the write model in the repository
        dataObjectWriteRepository.delete(command);

    }

    private DataObjectAggregate rehydrate(UUID uuid, String errorText){

        List< BaseEvent > eventStream = eventStore.loadEventsForAggregate(uuid);

        if (eventStream.isEmpty()) {
            throw new IllegalArgumentException("DataObject not found with ID: " + uuid);
        }

        // Create the aggregate and rehydrate it
        DataObjectAggregate aggregate = new DataObjectAggregate(uuid);
        aggregate.rehydrate(eventStream);

        if (aggregate.isDeleted()) {
            throw new IllegalStateException(errorText);
        }
        return aggregate;
    }
}
