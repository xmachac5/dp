package org.master.command.dataObject.commands;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateDataObjectCommand (
    @NotBlank
    @Size(min = 5, max = 100) String name,
    @NotBlank
    String description,
    Boolean trackChanges,
    Boolean softDelete,
    @NotNull JsonNode columns,
    @NotNull Boolean hasForm
){}
