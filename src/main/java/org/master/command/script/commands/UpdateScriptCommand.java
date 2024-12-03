package org.master.command.script.commands;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record UpdateScriptCommand (
        @NotNull UUID id,
        @NotNull JsonNode variables,
        @NotBlank @Size(min = 5, max = 100) String name,
        String code
){}
