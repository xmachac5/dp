package org.master.events.form;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.master.events.BaseEvent;

import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
public class FormUpdatedEvent extends BaseEvent {
    private Integer columns;
    private List<Integer> rowHeights;
    private UUID primaryLanguageId;
    private List<Integer> rowMaxHeights;
    private JsonNode columnMapping;
    private JsonNode definition;
    private UUID dataObjectUUID;

    @Builder
    @JsonCreator
    public FormUpdatedEvent(
            @JsonProperty(value = "id", required = true) UUID id,
            @JsonProperty("columns") Integer columns,
            @JsonProperty("rowHeights") List<Integer> rowHeights,
            @JsonProperty("primaryLanguageId") UUID primaryLanguageId,
            @JsonProperty("rowMaxHeights") List<Integer> rowMaxHeights,
            @JsonProperty("columnMapping") JsonNode columnMapping,
            @JsonProperty("definition") JsonNode definition,
            @JsonProperty("dataObjectUUID") UUID dataObjectUUID
    ) {
        super(id);
        this.columns = columns;
        this.rowHeights = rowHeights;
        this.primaryLanguageId = primaryLanguageId;
        this.rowMaxHeights = rowMaxHeights;
        this.columnMapping = columnMapping;
        this.definition = definition;
        this.dataObjectUUID = dataObjectUUID;
    }
}
