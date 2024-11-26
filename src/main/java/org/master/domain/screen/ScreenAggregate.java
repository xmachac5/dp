package org.master.domain.screen;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;
import org.master.domain.AggregateRoot;
import org.master.dto.screen.ScreenCreateDTO;
import org.master.events.BaseEvent;
import org.master.events.screen.ScreenCreatedEvent;
import org.master.events.screen.ScreenUpdatedEvent;
import java.util.UUID;

@Getter
public class ScreenAggregate extends AggregateRoot {

    private String name;
    private JsonNode data;

    public ScreenAggregate(UUID id) {
        super(id);
    }

    // Factory method to create a new ScreenAggregate
    public static ScreenAggregate createScreen(ScreenCreateDTO screenCreateDTO) {
        UUID id = UUID.randomUUID(); // Generate a new UUID for the aggregate
        ScreenAggregate aggregate = new ScreenAggregate(id);
        // Creating the ScreenCreatedEvent with all necessary data
        ScreenCreatedEvent event = new ScreenCreatedEvent(
                id,
                screenCreateDTO.getName(),
                screenCreateDTO.getData()
        );
        aggregate.apply(event);
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
