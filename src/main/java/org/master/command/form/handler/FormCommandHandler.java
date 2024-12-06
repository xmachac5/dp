package org.master.command.form.handler;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.master.command.form.commands.CreateFormCommand;
import org.master.command.form.commands.DeleteFormCommand;
import org.master.command.form.commands.UpdateFormCommand;
import org.master.domain.form.FormAggregate;
import org.master.events.BaseEvent;
import org.master.eventsourcing.EventStore;
import org.master.repository.form.FormWriteRepository;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class FormCommandHandler {
    @Inject
    EventStore eventStore;

    @Inject
    FormWriteRepository formWriteRepository;


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

        FormAggregate aggregate = rehydrate(command.id(), "Cannot update deleted Form");

        // Apply the update command
        aggregate.updateForm(command);

        // Persist and publish events
        eventStore.saveAndPublish(aggregate);

        // Update the write model in the repository
        formWriteRepository.update(command);
    }

    @Transactional
    public void handle(DeleteFormCommand command) {

        FormAggregate aggregate = rehydrate(command.uuid(), "Cannot delete deleted Form");

        // Apply the update command
        aggregate.deleteForm(command);

        // Persist and publish events
        eventStore.saveAndPublish(aggregate);

        // Update the write model in the repository
        formWriteRepository.delete(command);

    }

    private FormAggregate rehydrate(UUID uuid, String errorText){

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
}
