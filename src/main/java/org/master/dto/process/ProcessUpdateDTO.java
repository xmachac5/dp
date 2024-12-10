package org.master.dto.process;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Setter
@Getter
@AllArgsConstructor
public class ProcessUpdateDTO {
    @NotBlank
    @Size(min = 5, max = 100) String name;
    @Schema(
            description = "JSON of process variables",
            example = "{ \"variables\": [ " +
                    "{\"name\": \"input_variable\", \"type\": \"Input\"}," +
                    "{\"name\": \"output_variable\", \"type\": \"Output\"}" +
                    "{\"name\": \"local_variable\", \"type\": \"Local\"}" +
                    "]}"
    )
    JsonNode variables;
}
