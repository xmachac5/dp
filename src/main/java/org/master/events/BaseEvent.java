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
import org.master.events.screen.ScreenDeletedEvent;
import org.master.events.screen.ScreenUpdatedEvent;
import org.master.events.script.ScriptCreatedEvent;
import org.master.events.script.ScriptDeletedEvent;
import org.master.events.script.ScriptUpdatedEvent;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME, // Use simple names for types
        property = "type" // Property name for the type
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = ScreenCreatedEvent.class, name = "ScreenCreatedEvent"),
        @JsonSubTypes.Type(value = ScreenUpdatedEvent.class, name = "ScreenUpdateEvent"),
        @JsonSubTypes.Type(value = ScreenDeletedEvent.class, name = "ScreenDeletedEvent"),
        @JsonSubTypes.Type(value = ScriptCreatedEvent.class, name = "ScriptCreatedEvent"),
        @JsonSubTypes.Type(value = ScriptUpdatedEvent.class, name = "ScriptUpdatedEvent"),
        @JsonSubTypes.Type(value = ScriptDeletedEvent.class, name = "ScriptDeletedEvent")
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
