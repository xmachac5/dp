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
public class DataObjectPublishedEvent extends BaseEvent {
    private String description;
    private Boolean trackChanges;
    private Boolean softDelete;
    private JsonNode columns;

    @Builder
    @JsonCreator
    public DataObjectPublishedEvent(
            @JsonProperty(value = "id", required = true) UUID id,
            @JsonProperty("description") String description,
            @JsonProperty("trackChanges") Boolean trackChanges,
            @JsonProperty("softDelete") Boolean softDelete,
            @JsonProperty("columns") JsonNode columns
    ) {
        super(id);
        this.columns = columns;
        this.description = description;
        this.trackChanges = trackChanges;
        this.softDelete = softDelete;
    }
}