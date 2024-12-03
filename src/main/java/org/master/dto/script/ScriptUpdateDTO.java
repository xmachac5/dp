package org.master.dto.script;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Setter
@Getter
@AllArgsConstructor
public class ScriptUpdateDTO {
    @NotNull
    @Schema(
            description = "JSON variables structure for script creation",
            example = "{ \"name\": \"input_variable\", \"data_type\": \"String\", \"type\": \"input\" }"
    )
    private JsonNode variables;

    private String code;

    @NotBlank
    @Size(min = 5, max = 100)
    private String name;
}
