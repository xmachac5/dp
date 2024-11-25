package org.master.domain.screen;

import com.fasterxml.jackson.databind.JsonNode;
import org.master.domain.AggregateRoot;
import org.master.dto.screen.ScreenCreateDTO;
import org.master.events.BaseEvent;
import org.master.events.screen.ScreenCreatedEvent;
import org.master.events.screen.ScreenUpdatedEvent;
import java.util.UUID;

public class ScreenAggregate extends AggregateRoot {

    private String name;
    private JsonNode data; // Assuming data is stored as a simple string for this example

    public ScreenAggregate(UUID id) {
        super(id);
    }

    // Factory method to create a new ScreenAggregate
    public static ScreenAggregate createScreen(ScreenCreateDTO screenCreateDTO) {
        ScreenAggregate aggregate = new ScreenAggregate(UUID.randomUUID());
        aggregate.apply(new ScreenCreatedEvent(aggregate.getId(), screenCreateDTO.getName(), screenCreateDTO.getData()));
        return aggregate;
    }

    public void updateScreen(String name, JsonNode data) {
        apply(new ScreenUpdatedEvent(this.getId(), name, data));
    }

    @Override
    protected void when(BaseEvent event) {
        if (event instanceof ScreenCreatedEvent created) {
            this.name = created.getName();
            this.data = created.getData();
        } else if (event instanceof ScreenUpdatedEvent updated) {
            this.name = updated.getName();
            this.data = updated.getData();
        }
    }
}
