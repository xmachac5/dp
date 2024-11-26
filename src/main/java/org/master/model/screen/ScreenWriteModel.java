package org.master.model.screen;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.master.events.BaseEvent;
import org.master.events.screen.ScreenCreatedEvent;
import org.master.model.BaseEntity;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "screens_write_model")
public class ScreenWriteModel extends BaseEntity {

    @Column(columnDefinition = "json")
    @JdbcTypeCode(SqlTypes.JSON)
    private JsonNode data;

    private String name;

    @Getter
    @Transient
    private List<BaseEvent> uncommittedChanges = new ArrayList<>();

    public void applyEvent(BaseEvent event) {
        // Apply the event to the aggregate
        // Update state based on event type
        if (event instanceof ScreenCreatedEvent) {
            this.name = ((ScreenCreatedEvent) event).getName();
            this.data = ((ScreenCreatedEvent) event).getData();
        }

        // Add the event to uncommitted changes
        uncommittedChanges.add(event);
    }

    public void markChangesAsCommitted() {
        uncommittedChanges.clear();
    }

}
