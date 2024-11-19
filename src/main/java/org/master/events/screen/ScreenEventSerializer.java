package org.master.events.screen;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serializer;
import java.util.Map;

public class ScreenEventSerializer implements Serializer<ScreenEvent> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        // No configuration needed
    }

    @Override
    public byte[] serialize(String topic, ScreenEvent data) {
        try {
            return objectMapper.writeValueAsBytes(data);
        } catch (Exception e) {
            throw new RuntimeException("Error serializing ScreenEvent", e);
        }
    }

    @Override
    public void close() {
        // No resources to close
    }
}
