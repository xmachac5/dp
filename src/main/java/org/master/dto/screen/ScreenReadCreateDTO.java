package org.master.dto.screen;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.UUID;

public class ScreenReadCreateDTO {

    private UUID id;

    private JsonNode data;

    private String name;

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
