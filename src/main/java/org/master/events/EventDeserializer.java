package org.master.events;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.kafka.common.serialization.Deserializer;

import jakarta.inject.Singleton;

import java.util.Map;

@Singleton
public class EventDeserializer implements Deserializer<BaseEvent> {

    private final ObjectMapper objectMapper;

    public EventDeserializer() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
        this.objectMapper.activateDefaultTyping(
                objectMapper.getPolymorphicTypeValidator(),
                ObjectMapper.DefaultTyping.NON_FINAL
        );
    }

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        // No configuration needed
    }

    @Override
    public BaseEvent deserialize(String topic, byte[] data) {
        try {
            return objectMapper.readValue(data, BaseEvent.class);
        } catch (Exception e) {
            throw new RuntimeException("Error deserializing " + BaseEvent.class, e);
        }
    }

    public BaseEvent deserialize(String payload) {
        try {
            // Use polymorphism or type hints to deserialize into the correct event type
            return objectMapper.readValue(payload, BaseEvent.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error deserializing event payload", e);
        }
    }

    @Override
    public void close() {
        // No resources to close
    }
}