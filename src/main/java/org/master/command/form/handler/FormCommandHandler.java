package org.master.command.form.handler;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.master.command.dataObject.commands.CreateDataObjectCommand;
import org.master.command.dataObject.commands.DeleteDataObjectCommand;
import org.master.command.dataObject.commands.PublishDataObjectCommand;
import org.master.command.dataObject.commands.UpdateDataObjectCommand;
import org.master.command.form.commands.*;
import org.master.domain.dataObject.DataObjectAggregate;
import org.master.domain.form.FormAggregate;
import org.master.events.BaseEvent;
import org.master.eventsourcing.EventStore;
import org.master.model.dataObject.DataObjectsWriteModel;
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

        if (aggregate.getDataObjectUUID() != null){
            throw new IllegalArgumentException("Cannot update Form with DO with this endpoint");
        }

        // Apply the update command
        aggregate.updateForm(command);

        // Persist and publish events
        eventStore.saveAndPublish(aggregate);

        // Update the write model in the repository
        formWriteRepository.update(command);
    }

    @Transactional
    public void handle(PublishFormCommand command) {

        FormAggregate aggregate = rehydrateForm(command.id(), "Cannot update deleted Form");

        if (aggregate.getDataObjectUUID() != null){
            throw new IllegalArgumentException("Cannot publish Form with DO with this endpoint");
        }

        // Apply the update command
        aggregate.publishForm(command);

        // Persist and publish events
        eventStore.saveAndPublish(aggregate);

    }

    @Transactional
    public void handle(CreateFormWithDOCommand command) {

        CreateDataObjectCommand createDataObjectCommand = new CreateDataObjectCommand(
                command.name(),
                "",
                command.trackChanges(),
                command.softDelete(),
                command.columnMapping(),
                true
        );

        // Use factory method to create a new DO aggregate
        DataObjectAggregate dataObjectAggregate =  DataObjectAggregate.createDataObject(createDataObjectCommand);

        // Persist and publish DO events
        eventStore.saveAndPublish(dataObjectAggregate);

        DataObjectsWriteModel dataObjectsWriteModel = dataObjectWriteRepository.create(dataObjectAggregate.getId(),
                createDataObjectCommand, 1);

        CreateFormCommand createFormCommand = new CreateFormCommand(
                command.name(),
                command.columns(),
                command.rowHeights(),
                command.primaryLanguageId(),
                command.rowMaxHeights(),
                command.columnMapping(),
                command.definition(),
                dataObjectsWriteModel
        );

        // Use factory method to create a new form aggregate
        FormAggregate formAggregate = FormAggregate.createForm(dataObjectAggregate.getId(), createFormCommand);

        // Persist and publish form events
        eventStore.saveAndPublish(formAggregate);

        formWriteRepository.create(formAggregate.getId(), createFormCommand);

    }

    @Transactional
    public void handle(UpdateFormWithDOCommand command) {

        FormAggregate formAggregate = rehydrateForm(command.id(), "Cannot update deleted Form");

        if (formAggregate.getDataObjectUUID() == null){
            throw new IllegalArgumentException("Cannot update Form without DO with this endpoint");
        }

        DataObjectAggregate dataObjectAggregate = rehydrateDO(command.id(), "Cannot update deleted DO");

        CreateDataObjectCommand createDataObjectCommand = new CreateDataObjectCommand(
                formAggregate.getName(),
                "",
                command.trackChanges(),
                command.softDelete(),
                command.columnMapping(),
                true
        );

        UpdateDataObjectCommand updateDataObjectCommand = new UpdateDataObjectCommand(
                command.id(),
                "",
                command.trackChanges(),
                command.softDelete(),
                command.columnMapping()
        );

        Integer new_version = formWriteRepository.latestVersion(command.id()) + 1;

        DataObjectsWriteModel dataObjectsWriteModel = dataObjectWriteRepository.create(UUID.randomUUID(), createDataObjectCommand, new_version);

        UpdateFormCommand updateFormCommand = new UpdateFormCommand(
                command.id(),
                command.columns(),
                command.rowHeights(),
                command.primaryLanguageId(),
                command.rowMaxHeights(),
                command.columnMapping(),
                command.definition(),
                dataObjectsWriteModel
        );

        // Apply the update command
        formAggregate.updateForm(updateFormCommand);

        dataObjectAggregate.updateDataObject(updateDataObjectCommand);

        // Persist and publish events
        eventStore.saveAndPublish(formAggregate);

        eventStore.saveAndPublish(dataObjectAggregate);

        // Update the write model in the repository
        formWriteRepository.update(updateFormCommand);
    }

    @Transactional
    public void handle(PublishFormWithDOCommand command) {

        FormAggregate formAggregate = rehydrateForm(command.id(), "Cannot publish deleted Form");

        if (formAggregate.getDataObjectUUID() == null){
            throw new IllegalArgumentException("Cannot publish Form without DO with this endpoint");
        }

        DataObjectAggregate dataObjectAggregate = rehydrateDO(command.id(), "Cannot publish deleted DO");

        PublishDataObjectCommand publishDataObjectCommand = new PublishDataObjectCommand(command.id());

        PublishFormCommand publishFormCommand = new PublishFormCommand(command.id());

        // Apply the update command
        formAggregate.publishForm(publishFormCommand);

        dataObjectAggregate.publishDataObject(publishDataObjectCommand);

        // Persist and publish events
        eventStore.saveAndPublish(formAggregate);

        eventStore.saveAndPublish(dataObjectAggregate);
    }

    @Transactional
    public void handle(DeleteFormCommand command) {

        FormAggregate aggregate = rehydrateForm(command.uuid(), "Cannot delete deleted Form Handler");

        if (aggregate.getDataObjectUUID() != null){
            throw new IllegalArgumentException("Cannot delete Form with DO with this endpoint");
        }

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

        if (formAggregate.getDataObjectUUID() == null){
            throw new IllegalArgumentException("Cannot delete Form without DO with this endpoint");
        }

        DataObjectAggregate dataObjectAggregate = rehydrateDO(command.uuid(), "Cannot delete deleted DO Handler");

        // Apply the update command
        formAggregate.deleteForm(deleteFormCommand);

        dataObjectAggregate.deleteDataObject(deleteDataObjectCommand);

        // Persist and publish events
        eventStore.saveAndPublish(formAggregate);

        eventStore.saveAndPublish(dataObjectAggregate);

        // Update the write model in the repository
        formWriteRepository.delete(command);
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
