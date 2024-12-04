package org.master.dto.screen;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.List;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
public class ScreenCreateDTO {

    @NotNull
    @Schema(
            description = "Main JSON data structure for screen creation",
            example = "{\"widgets\": [" +
                    " {\"type\": \"grid\", \"settings\": { \"object\": \"list\" }}," +
                    " {\"type\": \"header\", \"expression\": \"name\" } " +
                    "]}")
    private JsonNode data;

    @NotBlank
    @Size(min = 5, max = 100)
    private String name;

    @NotNull
    private Integer columns;

    @NotNull
    private List<Integer> rowHeights;

    @NotNull
    private UUID primaryLanguageId;

    @NotBlank
    private String url;

    private List<Integer> rowMaxHeights;

    @Schema(
            description = "JSON of local variables for screen creation",
            example = "{ \"variables\": [ " +
                    "{\"name\": \"variable_1\", \"data_type\": \"Integer\", \"type\": \"input\"}," +
                    "{\"name\": \"variable_2\", \"data_type\": \"String\", \"type\": \"local\"}" +
                    "]}"
    )
    private JsonNode locals;

    @Schema(
            description = "JSON of variables initialization for screen creation",
            example = "{ \"variables\": [ " +
                    "{\"name\": \"variable_1\", \"table_name\": \"null\", \"where\": \"null\"}," +
                    "{\"name\": \"variable_2\", \"table_name\": \"cars\", \"where\": \"id = variable_1\"}" +
                    "]}"
    )
    private JsonNode variableInit;

    @Schema(
            description = "JSON of variables initialization mapping for screen creation",
            example = "{ \"variables\": [ " +
                    "{\"name\": \"variable_1\", \"expression\": \"if variable_1 == null, 0, variable_1\"}," +
                    "{\"name\": \"variable_2\", \"expression\": \"null\"}" +
                    "]}"
    )
    private JsonNode variableInitMapping;

    @Schema(
            description = "JSON of visual background setting structure for screen creation",
            example = "{ \"background_colour\": \"colour\", \"background_opacity\": \"opacity\" }"
    )    private JsonNode background;

    private String title;
}
