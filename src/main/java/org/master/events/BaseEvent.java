package org.master.events;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.master.events.dataObject.DataObjectCreatedEvent;
import org.master.events.dataObject.DataObjectDeletedEvent;
import org.master.events.dataObject.DataObjectUpdatedEvent;
import org.master.events.form.FormCreatedEvent;
import org.master.events.form.FormDeletedEvent;
import org.master.events.form.FormUpdatedEvent;
import org.master.events.process.*;
import org.master.events.process.task.TaskCreatedEvent;
import org.master.events.process.task.TaskDeletedEvent;
import org.master.events.process.task.TaskUpdatedEvent;
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
        @JsonSubTypes.Type(value = ScriptDeletedEvent.class, name = "ScriptDeletedEvent"),
        @JsonSubTypes.Type(value = FormCreatedEvent.class, name = "FormCreatedEvent"),
        @JsonSubTypes.Type(value = FormUpdatedEvent.class, name = "FormUpdatedEvent"),
        @JsonSubTypes.Type(value = FormDeletedEvent.class, name = "FormDeletedEvent"),
        @JsonSubTypes.Type(value = DataObjectCreatedEvent.class, name = "DataObjectCreatedEvent"),
        @JsonSubTypes.Type(value = DataObjectUpdatedEvent.class, name = "DataObjectUpdatedEvent"),
        @JsonSubTypes.Type(value = DataObjectDeletedEvent.class, name = "DataObjectDeletedEvent"),
        @JsonSubTypes.Type(value = ProcessCreatedEvent.class, name = "ProcessCreatedEvent"),
        @JsonSubTypes.Type(value = ProcessUpdatedEvent.class, name = "ProcessUpdatedEvent"),
        @JsonSubTypes.Type(value = ProcessDeletedEvent.class, name = "ProcessDeletedEvent"),
        @JsonSubTypes.Type(value = ProcessPublishedEvent.class, name = "ProcessPublishedEvent"),
        @JsonSubTypes.Type(value = ProcessConceptEvent.class, name = "ProcessConceptEvent"),
        @JsonSubTypes.Type(value = TaskCreatedEvent.class, name = "TaskCreatedEvent"),
        @JsonSubTypes.Type(value = TaskUpdatedEvent.class, name = "TaskUpdatedEvent"),
        @JsonSubTypes.Type(value = TaskDeletedEvent.class, name = "TaskDeletedEvent")
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
