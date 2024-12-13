package org.master.command.form.commands;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.master.model.dataObject.DataObjectsWriteModel;

import java.util.List;
import java.util.UUID;

public record CreateFormCommand (
        @NotBlank @Size(min = 5, max = 100) String name,
        @NotNull Integer columns,
        @NotNull List<Integer> rowHeights,
        @NotNull UUID primaryLanguageId,
        List<Integer> rowMaxHeights,
        JsonNode columnMapping,
        @NotNull JsonNode definition,
        DataObjectsWriteModel dataObjectsWriteModel
){}
