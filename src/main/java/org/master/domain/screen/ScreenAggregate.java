package org.master.domain.screen;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.master.command.screen.commands.CreateScreenCommand;
import org.master.command.screen.commands.UpdateScreenCommand;
import org.master.domain.AggregateRoot;
import org.master.dto.screen.ScreenCreateDTO;
import org.master.events.BaseEvent;
import org.master.events.screen.ScreenCreatedEvent;
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
        apply(new ScreenUpdatedEvent(updateScreenCommand.id(), updateScreenCommand.name(), updateScreenCommand.data(),
                updateScreenCommand.columns(), updateScreenCommand.rowHeights(), updateScreenCommand.primaryLanguageId(),
                updateScreenCommand.url(), updateScreenCommand.rowMaxHeights(), updateScreenCommand.locals(),
                updateScreenCommand.variableInit(), updateScreenCommand.variableInitMapping(), updateScreenCommand.background(),
                updateScreenCommand.title()));
    }

    @Override
    protected void when(BaseEvent event) {
        if (event instanceof ScreenCreatedEvent created) {
            handle(created);
        } else if (event instanceof ScreenUpdatedEvent updated) {
            //handle(updated);
        }
    }

    private void handle(final ScreenCreatedEvent screenCreatedEvent){
        this.name = screenCreatedEvent.getName();
        this.data = screenCreatedEvent.getData();
        this.columns = screenCreatedEvent.getColumns();
        this.rowHeights = screenCreatedEvent.getRowHeights();
        this.primaryLanguageId = screenCreatedEvent.getPrimaryLanguageId();
        this.url = screenCreatedEvent.getUrl();
        this.rowMaxHeights = screenCreatedEvent.getRowMaxHeights();
        this.locals = screenCreatedEvent.getLocals();
        this.variableInit = screenCreatedEvent.getVariableInit();
        this.variableInitMapping = screenCreatedEvent.getVariableInitMapping();
        this.background = screenCreatedEvent.getBackground();
        this.title = screenCreatedEvent.getTitle();
    }
}
