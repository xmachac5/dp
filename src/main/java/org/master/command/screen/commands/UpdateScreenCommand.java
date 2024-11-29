package org.master.command.screen.commands;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.UUID;

public record UpdateScreenCommand (
    @NotNull
    UUID id,
    @NotNull
    JsonNode data,
    @NotBlank
    @Size(min = 5, max = 100) String name,
    @NotNull Integer columns,
    @NotBlank
    List<Integer> rowHeights,
    @NotBlank
    UUID primaryLanguageId,
    @NotBlank String url,
    List<Integer> rowMaxHeights,
    JsonNode locals,
    JsonNode variableInit,
    JsonNode variableInitMapping,
    JsonNode background,
    String title
){}
