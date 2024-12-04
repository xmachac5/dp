package org.master.command.dataObject;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateDataObjectCommand (
        @NotBlank
        String description,
        Boolean trackChanges,
        Boolean softDelete,
        @NotNull JsonNode columns
){}
