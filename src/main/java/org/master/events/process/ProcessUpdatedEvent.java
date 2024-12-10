package org.master.events.process;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.master.events.BaseEvent;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
public class ProcessUpdatedEvent extends BaseEvent {
    private String name;
    private JsonNode variables;

    @Builder
    @JsonCreator
    public ProcessUpdatedEvent(
            @JsonProperty(value = "id", required = true) UUID id,
            @JsonProperty("name") String name,
            @JsonProperty("variables") JsonNode variables
    ) {
        super(id);
        this.name = name;
        this.variables = variables;
    }
}
