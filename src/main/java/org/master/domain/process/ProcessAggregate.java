package org.master.domain.process;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.master.command.process.commands.*;
import org.master.domain.AggregateRoot;
import org.master.events.BaseEvent;
import org.master.events.process.*;

import java.util.UUID;

@Getter
@EqualsAndHashCode(callSuper = false)
public class ProcessAggregate extends AggregateRoot {

    private String name;
    private Integer processVersion;
    private JsonNode variables;
    private Boolean published;


    public ProcessAggregate(UUID id) {
        super(id);
    }

    // Factory method to create a new ProcessAggregate
    public static ProcessAggregate createProcess(CreateProcessCommand createProcessCommand) {
        UUID id = UUID.randomUUID(); // Generate a new UUID for the aggregate
        ProcessAggregate aggregate = new ProcessAggregate(id);
        // Creating the ProcessCreatedEvent with all necessary data
        ProcessCreatedEvent event = new ProcessCreatedEvent(
                id,
                createProcessCommand.name(),
                createProcessCommand.variables()
        );
        aggregate.apply(event);
        return aggregate;
    }

    public void updateProcess(UpdateProcessCommand updateProcessCommand) {
        // Creating the ProcessUpdatedEvent with all necessary data
        ProcessUpdatedEvent event = new ProcessUpdatedEvent(
                updateProcessCommand.id(),
                updateProcessCommand.name(),
                updateProcessCommand.variables()
        );
        this.apply(event);
    }

    public void deleteProcess(DeleteProcessCommand deleteProcessCommand) {
        // Creating the ProcessDeletedEvent with process id
        ProcessDeletedEvent event = new ProcessDeletedEvent(deleteProcessCommand.uuid());
        this.apply(event);
    }

    public void conceptProcess(ConceptProcessCommand conceptProcessCommand) {
        // Creating the ProcessConceptEvent with process id
        ProcessConceptEvent event = new ProcessConceptEvent(conceptProcessCommand.uuid());
        this.apply(event);
    }

    public void publishProcess(PublishProcessCommand publishProcessCommand) {
        // Creating the ProcessPublishedEvent with process id
        ProcessPublishedEvent event = new ProcessPublishedEvent(
                publishProcessCommand.uuid(),
                this.name,
                this.variables
        );
        this.apply(event);
    }

    @Override
    protected void when(BaseEvent event) {
        if (event instanceof ProcessCreatedEvent created) {
            handle(created);
        } else if (event instanceof ProcessUpdatedEvent updated) {
            handle(updated);
        } else if (event instanceof ProcessDeletedEvent deleted) {
            handle(deleted);
        } else if (event instanceof ProcessConceptEvent concept) {
            handle(concept);
        } else if (event instanceof ProcessPublishedEvent publishedE) {
            handle(publishedE);
        }
    }

    private void handle(final ProcessCreatedEvent processCreatedEvent){
        this.name = processCreatedEvent.getName();
        this.published = false;
        this.variables = processCreatedEvent.getVariables();
        this.processVersion = 1;
    }

    private void handle(final ProcessUpdatedEvent processUpdatedEvent){
        this.name = processUpdatedEvent.getName();
        this.variables = processUpdatedEvent.getVariables();

    }

    private void handle(ProcessDeletedEvent processDeletedEvent){
        super.markAsDeleted();
    }

    private void handle(ProcessConceptEvent processConceptEvent){
        this.published = false;
    }

    private void handle(ProcessPublishedEvent processPublishedEvent){
        this.processVersion = this.processVersion + 1;
        this.published = true;
    }

}
