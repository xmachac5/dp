package org.master.events.screen;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.json.Json;
import jakarta.json.JsonString;

import java.time.LocalDateTime;
import java.util.UUID;

public class ScreenEvent {
    private ScreenEventType type;
    private UUID screenId;
    private LocalDateTime timestamp;
    private String name;
    private JsonNode data;

    public ScreenEventType getType() {
        return type;
    }

    public void setType(ScreenEventType type) {
        this.type = type;
    }

    public UUID getScreenId() {
        return screenId;
    }

    public void setScreenId(UUID screenId) {
        this.screenId = screenId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public JsonNode getData() {
        return data;
    }

    public void setData(JsonNode data) {
        this.data = data;
    }
}
