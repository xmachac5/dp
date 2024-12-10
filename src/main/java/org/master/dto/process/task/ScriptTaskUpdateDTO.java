package org.master.dto.process.task;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
public class ScriptTaskUpdateDTO {
    @NotBlank
    @Size(min = 5, max = 100) String name;
    @Schema(
            description = "JSON of task variables",
            example = "{ \"variables\": [ " +
                    "{\"name\": \"input_variable\", \"type\": \"Input\"}," +
                    "{\"name\": \"output_variable\", \"type\": \"Output\"}" +
                    "]}"
    )
    JsonNode variableMapping;
    @NotNull UUID scriptWriteModel;
    @NotNull UUID processVersionWriteModel;
}
