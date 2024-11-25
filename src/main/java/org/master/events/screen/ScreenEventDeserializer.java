package org.master.events.screen;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.kafka.common.serialization.Deserializer;
import java.util.Map;

public class ScreenEventDeserializer implements Deserializer<ScreenCreatedEvent> {

    private final ObjectMapper objectMapper;

    public ScreenEventDeserializer() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        // No configuration needed
    }

    @Override
    public ScreenCreatedEvent deserialize(String topic, byte[] data) {
        try {
            return objectMapper.readValue(data, ScreenCreatedEvent.class);
        } catch (Exception e) {
            throw new RuntimeException("Error deserializing ScreenCreatedEvent", e);
        }
    }

    @Override
    public void close() {
        // No resources to close
    }
}
