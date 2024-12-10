package org.master.events.process.task;

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
public class TaskUpdatedEvent extends BaseEvent {
    private String name;
    private JsonNode variableMapping;
    private UUID targetEntityUuid;
    private JsonNode columnsMapping;

    @Builder
    @JsonCreator
    public TaskUpdatedEvent(
            @JsonProperty(value = "id", required = true) UUID id,
            @JsonProperty("name") String name,
            @JsonProperty("variableMapping") JsonNode variableMapping,
            @JsonProperty("targetEntityUuid") UUID targetEntityUuid,
            @JsonProperty("columnsMapping") JsonNode columnsMapping

    ) {
        super(id);
        this.name = name;
        this.variableMapping = variableMapping;
        this.targetEntityUuid = targetEntityUuid;
        this.columnsMapping = columnsMapping;
    }
}
