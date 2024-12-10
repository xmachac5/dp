package org.master.command.form.handler;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.master.command.dataObject.commands.CreateDataObjectCommand;
import org.master.command.dataObject.commands.DeleteDataObjectCommand;
import org.master.command.dataObject.commands.UpdateDataObjectCommand;
import org.master.command.form.commands.*;
import org.master.domain.dataObject.DataObjectAggregate;
import org.master.domain.form.FormAggregate;
import org.master.events.BaseEvent;
import org.master.eventsourcing.EventStore;
import org.master.repository.dataObject.DataObjectWriteRepository;
import org.master.repository.form.FormWriteRepository;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class FormCommandHandler {
    @Inject
    EventStore eventStore;

    @Inject
    FormWriteRepository formWriteRepository;

    @Inject DataObjectWriteRepository dataObjectWriteRepository;


    @Transactional
    public void handle(CreateFormCommand command) {

        // Use factory method to create a new aggregate
        FormAggregate aggregate = FormAggregate.createForm(command);

        // Persist and publish events
        eventStore.saveAndPublish(aggregate);

        formWriteRepository.create(aggregate.getId(), command);

    }

    @Transactional
    public void handle(UpdateFormCommand command) {

        FormAggregate aggregate = rehydrateForm(command.id(), "Cannot update deleted Form");

        // Apply the update command
        aggregate.updateForm(command);

        // Persist and publish events
        eventStore.saveAndPublish(aggregate);

        // Update the write model in the repository
        formWriteRepository.update(command);
    }

    @Transactional
    public void handle(CreateFormWithDOCommand command) {

        CreateFormCommand createFormCommand = new CreateFormCommand(
                command.name(),
                command.columns(),
                command.rowHeights(),
                command.primaryLanguageId(),
                command.rowMaxHeights(),
                command.columnMapping(),
                command.definition()
        );

        CreateDataObjectCommand createDataObjectCommand = new CreateDataObjectCommand(
                command.name(),
                "",
                command.trackChanges(),
                command.softDelete(),
                command.columnMapping()
        );

        // Use factory method to create a new form aggregate
        FormAggregate formAggregate = FormAggregate.createForm(createFormCommand);

        // Use factory method to create a new DO aggregate
        DataObjectAggregate dataObjectAggregate =  DataObjectAggregate.createDataObject(createDataObjectCommand,
                formAggregate.getId());

        // Persist and publish form events
        eventStore.saveAndPublish(formAggregate);

        // Persist and publish DO events
        eventStore.saveAndPublish(dataObjectAggregate);

        formWriteRepository.create(formAggregate.getId(), createFormCommand);

        dataObjectWriteRepository.create(dataObjectAggregate.getId(), createDataObjectCommand);

    }

    @Transactional
    public void handle(UpdateFormWithDOCommand command) {

        UpdateFormCommand updateFormCommand = new UpdateFormCommand(
                command.id(),
                command.columns(),
                command.rowHeights(),
                command.primaryLanguageId(),
                command.rowMaxHeights(),
                command.columnMapping(),
                command.definition()
        );

        UpdateDataObjectCommand updateDataObjectCommand = new UpdateDataObjectCommand(
                command.id(),
                "",
                command.trackChanges(),
                command.softDelete(),
                command.columnMapping()
        );

        FormAggregate formAggregate = rehydrateForm(command.id(), "Cannot update deleted Form");

        DataObjectAggregate dataObjectAggregate = rehydrateDO(command.id(), "Cannot update deleted DO");



        // Apply the update command
        formAggregate.updateForm(updateFormCommand);

        dataObjectAggregate.updateDataObject(updateDataObjectCommand);

        // Persist and publish events
        eventStore.saveAndPublish(formAggregate);

        eventStore.saveAndPublish(dataObjectAggregate);

        // Update the write model in the repository
        formWriteRepository.update(updateFormCommand);

        dataObjectWriteRepository.update(updateDataObjectCommand);
    }

    @Transactional
    public void handle(DeleteFormCommand command) {

        FormAggregate aggregate = rehydrateForm(command.uuid(), "Cannot delete deleted Form Handler");

        // Apply the update command
        aggregate.deleteForm(command);

        // Persist and publish events
        eventStore.saveAndPublish(aggregate);

        // Update the write model in the repository
        formWriteRepository.delete(command);

    }

    @Transactional
    public void handle(DeleteFormWithDOCommand command){

        DeleteDataObjectCommand deleteDataObjectCommand = new DeleteDataObjectCommand(command.uuid());

        DeleteFormCommand deleteFormCommand = new DeleteFormCommand(command.uuid());

        FormAggregate formAggregate = rehydrateForm(command.uuid(), "Cannot delete deleted Form Handler");

        DataObjectAggregate dataObjectAggregate = rehydrateDO(command.uuid(), "Cannot delete deleted DO Handler");

        // Apply the update command
        formAggregate.deleteForm(deleteFormCommand);

        dataObjectAggregate.deleteDataObject(deleteDataObjectCommand);

        // Persist and publish events
        eventStore.saveAndPublish(formAggregate);

        eventStore.saveAndPublish(dataObjectAggregate);

        // Update the write model in the repository
        formWriteRepository.delete(deleteFormCommand);

        dataObjectWriteRepository.delete(deleteDataObjectCommand);
    }

    private FormAggregate rehydrateForm(UUID uuid, String errorText){

        List<BaseEvent> eventStream = eventStore.loadEventsForAggregate(uuid);

        if (eventStream.isEmpty()) {
            throw new IllegalArgumentException("Form not found with ID: " + uuid);
        }

        // Create the aggregate and rehydrate it
        FormAggregate aggregate = new FormAggregate(uuid);
        aggregate.rehydrate(eventStream);

        if (aggregate.isDeleted()) {
            throw new IllegalStateException(errorText);
        }
        return aggregate;
    }

    private DataObjectAggregate rehydrateDO(UUID uuid, String errorText){

        List<BaseEvent> eventStream = eventStore.loadEventsForAggregate(uuid);

        if (eventStream.isEmpty()) {
            throw new IllegalArgumentException("Form not found with ID: " + uuid);
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
