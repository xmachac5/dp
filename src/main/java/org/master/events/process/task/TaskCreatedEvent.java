package org.master.events.process.task;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.master.events.BaseEvent;
import org.master.model.process.task.TaskType;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
public class TaskCreatedEvent extends BaseEvent {
    private String name;
    private JsonNode variableMapping;
    private TaskType type;
    private UUID targetEntityUuid;
    private UUID processWriteModel;
    private JsonNode columnsMapping;

    @Builder
    @JsonCreator
    public TaskCreatedEvent(
            @JsonProperty(value = "id", required = true) UUID id,
            @JsonProperty("name") String name,
            @JsonProperty("variableMapping") JsonNode variableMapping,
            @JsonProperty("type") TaskType type,
            @JsonProperty("targetEntityUuid") UUID targetEntityUuid,
            @JsonProperty("processWriteModel") UUID processWriteModel,
            @JsonProperty("columnsMapping") JsonNode columnsMapping

            ) {
        super(id);
        this.name = name;
        this.variableMapping = variableMapping;
        this.type = type;
        this.targetEntityUuid = targetEntityUuid;
        this.processWriteModel = processWriteModel;
        this.columnsMapping = columnsMapping;
    }
}
