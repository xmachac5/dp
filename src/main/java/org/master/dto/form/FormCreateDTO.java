package org.master.dto.form;

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
public class FormCreateDTO {
    @NotBlank
    @Size(min = 5, max = 100) String name;
    @NotNull
    Integer columns;
    @NotNull
    List<Integer> rowHeights;
    @NotNull
    UUID primaryLanguageId;
    List<Integer> rowMaxHeights;
    @Schema(
            description = "JSON of local variables for screen creation",
            example = "{ \"columns\": [ " +
                    "{\"name\": \"test_input\", \"data_type\": \"String\"}," +
                    "{\"name\": \"number_input\", \"data_type\": \"Integer\"}" +
                    "]}"
    )
    JsonNode columnMapping;
    @Schema(
            description = "JSON of local variables for screen creation",
            example =
                    "{\"definition\": \"definition\", \"background\": \"background\"}"
    )
    @NotNull JsonNode definition;
}
