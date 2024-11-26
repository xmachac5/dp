package org.master.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.master.events.BaseEvent;
import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, nullable = false, columnDefinition = "UUID")
    private UUID id;

    private UUID aggregateId;
    private String eventType;
    @Lob
    private String payload; // Serialized JSON payload
    private LocalDateTime timestamp;
    private Integer version;

    // Constructor for persistence
    public Event(BaseEvent baseEvent, String payload, Integer version) {
        this.aggregateId = baseEvent.getId();
        this.eventType = baseEvent.getEventType();
        this.payload = payload;
        this.timestamp = baseEvent.getTimestamp();
        this.version = version;
    }

    // Default constructor for JPA
    public Event() {}

}
