package org.master.events.script;

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
public class ScriptPublishedEvent extends BaseEvent {
    private JsonNode variables;
    private String name;
    private String code;

    @Builder
    @JsonCreator
    public ScriptPublishedEvent(
            @JsonProperty(value = "id", required = true) UUID id,
            @JsonProperty("name") String name,
            @JsonProperty("variables") JsonNode variables,
            @JsonProperty("code") String code
    ) {
        super(id);
        this.name = name;
        this.variables = variables;
        this.code = code;
    }
}
