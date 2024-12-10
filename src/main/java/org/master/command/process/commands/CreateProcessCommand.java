package org.master.command.process.commands;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateProcessCommand(
        @NotBlank
        @Size(min = 5, max = 100) String name,
        JsonNode variables
) {
}
