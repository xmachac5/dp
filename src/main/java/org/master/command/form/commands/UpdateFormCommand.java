package org.master.command.form.commands;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record UpdateFormCommand (
        @NotNull
        UUID id,
        @NotNull Integer columns,
        @NotNull List<Integer> rowHeights,
        @NotNull UUID primaryLanguageId,
        List<Integer> rowMaxHeights,
        JsonNode columnMapping,
        @NotNull JsonNode definition
){}
