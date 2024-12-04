package org.master.command.form;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.UUID;

public record UpdateFormCommand (
        @NotNull Integer columns,
        @NotNull List<Integer> rowHeights,
        @NotNull UUID primaryLanguageId,
        List<Integer> rowMaxHeights,
        JsonNode columnMapping,
        @NotNull JsonNode definition
){}
