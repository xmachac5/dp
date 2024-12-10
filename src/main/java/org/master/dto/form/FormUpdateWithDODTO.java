package org.master.dto.form;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.List;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
public class FormUpdateWithDODTO {
    @NotNull
    Integer columns;
    @NotNull
    List<Integer> rowHeights;
    @NotNull
    UUID primaryLanguageId;
    List<Integer> rowMaxHeights;
    @Schema(
            description = "JSON of column mapping variables for form with DO update",
            example = "{ \"columns\": [ " +
                    "{\"name\": \"text_input\", \"data_type\": \"String\", \"primaryKey\": \"False\"," +
                    "\"isFk\": \"False\", \"description\": \"Text\"}," +
                    "{\"name\": \"id\", \"data_type\": \"Integer\", \"primaryKey\": \"True\"," +
                    "\"isFk\": \"False\", \"description\": \"Primary Key\"}," +
                    "{\"name\": \"other_table\", \"data_type\": \"Integer\", \"primaryKey\": \"False\"," +
                    "\"isFk\": \"True\", \"description\": \"Other table id\"}" +
                    "]}"
    )
    JsonNode columnMapping;
    @Schema(
            description = "JSON of visual definition variables for form update",
            example =
                    "{\"definition\": \"definition\", \"background\": \"background\"}"
    )
    @NotNull JsonNode definition;
    Boolean trackChanges;
    Boolean softDelete;
}
