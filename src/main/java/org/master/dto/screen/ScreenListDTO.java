package org.master.dto.screen;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class ScreenListDTO {

    private UUID id;
    private JsonNode data;
    private String title;

    public ScreenListDTO(UUID id, String name, JsonNode data) {
        this.id = id;
        this.title = name;
        this.data = data;
    }

}
