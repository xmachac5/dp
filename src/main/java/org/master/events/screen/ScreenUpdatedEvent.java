package org.master.events.screen;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Builder;
import lombok.Data;
import org.master.events.BaseEvent;
import java.util.UUID;

@Data
public class ScreenUpdatedEvent extends BaseEvent {
    private final String name;
    private final JsonNode data;

    @Builder
    public ScreenUpdatedEvent(UUID id, String name, JsonNode data) {
        super(id);
        this.name = name;
        this.data = data;
    }
}
