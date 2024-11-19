package org.master.dto.screen;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.json.JsonString;

import java.util.UUID;

public class ScreenListDTO {

    private UUID id;
    private JsonNode data;
    private String name;

    public ScreenListDTO(UUID id, String name, JsonNode data) {
        this.id = id;
        this.name = name;
        this.data = data;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

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
