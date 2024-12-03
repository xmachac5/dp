package org.master.domain.screen;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.master.command.screen.commands.CreateScreenCommand;
import org.master.command.screen.commands.DeleteScreenCommand;
import org.master.command.screen.commands.UpdateScreenCommand;
import org.master.domain.AggregateRoot;
import org.master.events.BaseEvent;
import org.master.events.screen.ScreenCreatedEvent;
import org.master.events.screen.ScreenDeletedEvent;
import org.master.events.screen.ScreenUpdatedEvent;

import java.util.List;
import java.util.UUID;

@Getter
@EqualsAndHashCode(callSuper = false)
public class ScreenAggregate extends AggregateRoot {

    private JsonNode data;
    private String name;
    private Integer columns;
    private List<Integer> rowHeights;
    private UUID primaryLanguageId;
    private String url;
    private List<Integer> rowMaxHeights;
    private JsonNode locals;
    private JsonNode variableInit;
    private JsonNode variableInitMapping;
    private JsonNode background;
    private String title;

    public ScreenAggregate(UUID id) {
        super(id);
    }

    // Factory method to create a new ScreenAggregate
    public static ScreenAggregate createScreen(CreateScreenCommand createScreenCommand) {
        UUID id = UUID.randomUUID(); // Generate a new UUID for the aggregate
        ScreenAggregate aggregate = new ScreenAggregate(id);
        // Creating the ScreenCreatedEvent with all necessary data
        ScreenCreatedEvent event = new ScreenCreatedEvent(
                id,
                createScreenCommand.name(),
                createScreenCommand.data(),
                createScreenCommand.columns(),
                createScreenCommand.rowHeights(),
                createScreenCommand.primaryLanguageId(),
                createScreenCommand.url(),
                createScreenCommand.rowMaxHeights(),
                createScreenCommand.locals(),
                createScreenCommand.variableInit(),
                createScreenCommand.variableInitMapping(),
                createScreenCommand.background(),
                createScreenCommand.title()
        );
        aggregate.apply(event);
        return aggregate;
    }

    public void updateScreen(UpdateScreenCommand updateScreenCommand) {
        // Creating the ScreenUpdatedEvent with all necessary data
        ScreenUpdatedEvent event = new ScreenUpdatedEvent(updateScreenCommand.id(), updateScreenCommand.name(), updateScreenCommand.data(),
                updateScreenCommand.columns(), updateScreenCommand.rowHeights(), updateScreenCommand.primaryLanguageId(),
                updateScreenCommand.url(), updateScreenCommand.rowMaxHeights(), updateScreenCommand.locals(),
                updateScreenCommand.variableInit(), updateScreenCommand.variableInitMapping(), updateScreenCommand.background(),
                updateScreenCommand.title());
        this.apply(event);
    }

    public void deleteScreen(DeleteScreenCommand deleteScreenCommand) {
        // Creating the ScreenDeletedEvent with screen id
        ScreenDeletedEvent event = new ScreenDeletedEvent(deleteScreenCommand.uuid());
        this.apply(event);
    }

    @Override
    protected void when(BaseEvent event) {
        if (event instanceof ScreenCreatedEvent created) {
            handle(created);
        } else if (event instanceof ScreenUpdatedEvent updated) {
            handle(updated);
        } else if (event instanceof ScreenDeletedEvent) {
            handle();
        }
    }

    private void handle(final ScreenCreatedEvent screenCreatedEvent){
        setAggregateData(screenCreatedEvent.getName(), screenCreatedEvent.getData(), screenCreatedEvent.getColumns(),
                screenCreatedEvent.getRowHeights(), screenCreatedEvent.getPrimaryLanguageId(),
                screenCreatedEvent.getUrl(), screenCreatedEvent.getRowMaxHeights(), screenCreatedEvent.getLocals(),
                screenCreatedEvent.getVariableInit(), screenCreatedEvent.getVariableInitMapping(),
                screenCreatedEvent.getBackground(), screenCreatedEvent.getTitle());
    }

    private void handle(final ScreenUpdatedEvent screenUpdatedEvent){
        setAggregateData(screenUpdatedEvent.getName(), screenUpdatedEvent.getData(), screenUpdatedEvent.getColumns(),
                screenUpdatedEvent.getRowHeights(), screenUpdatedEvent.getPrimaryLanguageId(),
                screenUpdatedEvent.getUrl(), screenUpdatedEvent.getRowMaxHeights(), screenUpdatedEvent.getLocals(),
                screenUpdatedEvent.getVariableInit(), screenUpdatedEvent.getVariableInitMapping(),
                screenUpdatedEvent.getBackground(), screenUpdatedEvent.getTitle());
    }

    private void handle(){
        super.markAsDeleted();
    }

    private void setAggregateData(String name, JsonNode data, Integer columns, List<Integer> rowHeights,
                                  UUID primaryLanguageId, String url, List<Integer> rowMaxHeights, JsonNode locals,
                                  JsonNode variableInit, JsonNode variableInitMapping, JsonNode background,
                                  String title) {
        this.name = name;
        this.data = data;
        this.columns = columns;
        this.rowHeights = rowHeights;
        this.primaryLanguageId = primaryLanguageId;
        this.url = url;
        this.rowMaxHeights = rowMaxHeights;
        this.locals = locals;
        this.variableInit = variableInit;
        this.variableInitMapping = variableInitMapping;
        this.background = background;
        this.title = title;
    }
}
