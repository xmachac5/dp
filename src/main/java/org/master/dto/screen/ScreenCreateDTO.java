package org.master.dto.screen;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ScreenCreateDTO {

    //@Schema(type = SchemaType.OBJECT, description = "JSON data")
    @NotNull
    private JsonNode data;

    @NotBlank
    @Size(min = 5, max = 100)
    private String name;

    public JsonNode getData() {
        return data;
    }

    public void setData(JsonNode data) {
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
