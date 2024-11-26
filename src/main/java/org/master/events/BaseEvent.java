package org.master.events;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.master.events.screen.ScreenCreatedEvent;
import org.master.events.screen.ScreenUpdatedEvent;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME, // Use simple names for types
        include = JsonTypeInfo.As.PROPERTY, // Include the type as a property in the JSON
        property = "type" // Property name for the type
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = ScreenCreatedEvent.class, name = "ScreenCreatedEvent"),
        @JsonSubTypes.Type(value = ScreenUpdatedEvent.class, name = "ScreenUpdateEvent")
})
@Data
@NoArgsConstructor
@Getter
public abstract class BaseEvent {

    private UUID id; // ID of the related aggregate
    private String eventType; // The class name of the event
    private LocalDateTime timestamp; // When the event occurred

   public BaseEvent(UUID id) {
       Objects.requireNonNull(id);
       this.id = id;
       this.eventType = this.getClass().getSimpleName();
       this.timestamp = LocalDateTime.now();
   }

}
