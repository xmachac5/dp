package org.master.command.process.task.commands;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record CreateScreenTaskCommand(
        @NotBlank
        @Size(min = 5, max = 100) String name,
        JsonNode variableMapping,
        @NotNull UUID screenWriteModel,
        @NotNull UUID processVersionWriteModel
) {
}
