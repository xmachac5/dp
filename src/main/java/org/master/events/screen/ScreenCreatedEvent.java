package org.master.events.screen;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Builder;
import lombok.Data;
import org.master.events.BaseEvent;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class ScreenCreatedEvent extends BaseEvent {
    private JsonNode data;
    private String name;
    private Integer columns;
    private List<Integer> rowHeights;
    private UUID primaryLanguageId;
    private String url;
    private List<Integer> rowMaxHeights;
    private JsonNode locals;
    private JsonNode variableInit;
    private JsonNode variableInitMapping;
    private JsonNode background;
    private String title;

    @Builder
    @JsonCreator
    public ScreenCreatedEvent(
            @JsonProperty(value = "id", required = true) UUID id,
            @JsonProperty("name") String name,
            @JsonProperty("data") JsonNode data,
            @JsonProperty("columns") Integer columns,
            @JsonProperty("rowHeights") List<Integer> rowHeights,
            @JsonProperty("primaryLanguageId") UUID primaryLanguageId,
            @JsonProperty("url") String url,
            @JsonProperty("rowMaxHeights") List<Integer> rowMaxHeights,
            @JsonProperty("locals") JsonNode locals,
            @JsonProperty("variableInit") JsonNode variableInit,
            @JsonProperty("variableInitMapping") JsonNode variableInitMapping,
            @JsonProperty("background") JsonNode background,
            @JsonProperty("title") String title
    ) {
        super(id);
        this.name = name;
        this.data = data;
        this.columns = columns;
        this.rowHeights = rowHeights;
        this.primaryLanguageId = primaryLanguageId;
        this.url = url;
        this.rowMaxHeights = rowMaxHeights;
        this.locals = locals;
        this.variableInit = variableInit;
        this.variableInitMapping = variableInitMapping;
        this.background = background;
        this.title = title;
    }
}
