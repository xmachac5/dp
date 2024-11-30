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
            example = "{ \"widget\": \"grid\", \"settings\": { \"object\": \"list\" } }"
    )
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
            description = "Main JSON data structure for screen creation",
            example = "{ \"variable\": \"variable_name\", \"variable_type\": \"string\" }"
    )
    private JsonNode locals;

    @Schema(
            description = "Main JSON data structure for screen creation",
            example = "{ \"variable\": \"variable_name\", \"variable_value\": \"value\" }"
    )
    private JsonNode variableInit;

    @Schema(
            description = "Main JSON data structure for screen creation",
            example = "{ \"variable\": \"variable_name\", \"variable_value\": \"value\" }"
    )
    private JsonNode variableInitMapping;

    @Schema(
            description = "Main JSON data structure for screen creation",
            example = "{ \"background_colour\": \"colour\", \"background_opacity\": \"opacity\" }"
    )    private JsonNode background;

    private String title;
}
