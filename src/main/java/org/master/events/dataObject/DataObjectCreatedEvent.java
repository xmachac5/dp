package org.master.events.dataObject;

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
public class DataObjectCreatedEvent extends BaseEvent {
    private String name;
    private String description;
    private Boolean trackChanges;
    private Boolean softDelete;
    private JsonNode columns;
    private Boolean hasForm;

    @Builder
    @JsonCreator
    public DataObjectCreatedEvent(
            @JsonProperty(value = "id", required = true) UUID id,
            @JsonProperty("name") String name,
            @JsonProperty("description") String description,
            @JsonProperty("trackChanges") Boolean trackChanges,
            @JsonProperty("softDelete") Boolean softDelete,
            @JsonProperty("columns") JsonNode columns,
            @JsonProperty("hasForm") Boolean hasForm
    ) {
        super(id);
        this.name = name;
        this.columns = columns;
        this.description = description;
        this.trackChanges = trackChanges;
        this.softDelete = softDelete;
        this.hasForm = hasForm;
    }
}
