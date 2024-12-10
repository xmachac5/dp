package org.master.dto.dataObject;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Setter
@Getter
@AllArgsConstructor
public class DataObjectUpdateDTO {@NotBlank
    String description;
    Boolean trackChanges;
    Boolean softDelete;
    @NotNull
    @Schema(
            description = "JSON of columns variables for data object update",
            example = "{ \"columns\": [ " +
                    "{\"name\": \"text_input\", \"data_type\": \"String\", \"primaryKey\": \"False\"," +
                    " \"isFk\": \"False\", \"description\": \"Text\"}," +
                    "{\"name\": \"id\", \"data_type\": \"Integer\", \"primaryKey\": \"True\"," +
                    " \"isFk\": \"False\", \"description\": \"Primary Key\"}," +
                    "{\"name\": \"other_table\", \"data_type\": \"Integer\", \"primaryKey\": \"False\"," +
                    " \"isFk\": \"True\", \"description\": \"Other table id\"}" +
                    "]}"
    )
    JsonNode columns;

}
