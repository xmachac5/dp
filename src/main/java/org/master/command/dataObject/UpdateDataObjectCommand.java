package org.master.command.dataObject;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UpdateDataObjectCommand (
        @NotNull
        UUID id,
        @NotBlank
        String description,
        Boolean trackChanges,
        Boolean softDelete,
        @NotNull JsonNode columns
){}
