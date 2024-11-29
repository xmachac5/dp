package org.master.dto.screen;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
public class ScreenCreateDTO {

    //@Schema(type = SchemaType.OBJECT, description = "JSON data")
    @NotNull
    private JsonNode data;

    @NotBlank
    @Size(min = 5, max = 100)
    private String name;

    @NotNull
    private Integer columns;

    //@Schema(type = SchemaType.ARRAY, description = "Row heights as a list of integers")
    @NotBlank
    private List<Integer> rowHeights;

    @NotBlank
    private UUID primaryLanguageId;

    @NotBlank
    private String url;

    //@Schema(type = SchemaType.ARRAY, description = "Row maximum heights as a list of integers")
    private List<Integer> rowMaxHeights;

    //@Schema(type = SchemaType.OBJECT, description = "Localizations JSON data")
    private JsonNode locals;

    //@Schema(type = SchemaType.OBJECT, description = "Variable initial values JSON data")
    private JsonNode variableInit;

    //@Schema(type = SchemaType.OBJECT, description = "Variable initial mapping JSON data")
    private JsonNode variableInitMapping;

    //@Schema(type = SchemaType.OBJECT, description = "Background JSON data")
    private JsonNode background;

    private String title;
}
