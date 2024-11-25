package org.master.events;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Data
@NoArgsConstructor
public abstract class BaseEvent {
    private UUID id;
    private LocalDateTime timestamp;

   public BaseEvent(UUID id) {
       Objects.requireNonNull(id);
       this.id = id;
       this.timestamp = LocalDateTime.now();
   }

    public UUID getId() {
        return id;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
