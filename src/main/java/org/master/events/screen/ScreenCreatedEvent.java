package org.master.events.screen;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Builder;
import lombok.Data;
import org.master.events.BaseEvent;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class ScreenCreatedEvent extends BaseEvent {
    private String name;
    private JsonNode data;

    @Builder
    @JsonCreator
    public ScreenCreatedEvent(
            @JsonProperty(value = "id", required = true) UUID id,
            @JsonProperty("name") String name,
            @JsonProperty("data") JsonNode data
    ) {
        super(id);
        this.name = name;
        this.data = data;
    }
}
