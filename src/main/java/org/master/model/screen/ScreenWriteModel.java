package org.master.model.screen;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.master.events.BaseEvent;
import org.master.events.screen.ScreenCreatedEvent;
import org.master.model.BaseEntity;
import org.master.model.language.Language;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "screens_write_model")
public class ScreenWriteModel extends BaseEntity {

    @Column(columnDefinition = "json")
    @JdbcTypeCode(SqlTypes.JSON)
    private JsonNode data;
    private String name;
    private Integer columns;
    @Column(columnDefinition = "integer[]")
    @JdbcTypeCode(SqlTypes.ARRAY)
    private List<Integer> rowHeights;
    @ManyToOne
    @JoinColumn(name = "primaryLanguage", referencedColumnName = "id")
    private Language primaryLanguage;
    private String url;
    @Column(columnDefinition = "integer[]")
    @JdbcTypeCode(SqlTypes.ARRAY)
    private List<Integer> rowMaxHeights;
    @Column(columnDefinition = "json")
    @JdbcTypeCode(SqlTypes.JSON)
    private JsonNode locals;
    @Column(columnDefinition = "json")
    @JdbcTypeCode(SqlTypes.JSON)
    private JsonNode variableInit = JsonNodeFactory.instance.objectNode();
    @Column(columnDefinition = "json")
    @JdbcTypeCode(SqlTypes.JSON)
    private JsonNode variableInitMapping = JsonNodeFactory.instance.objectNode();
    @Column(columnDefinition = "json")
    @JdbcTypeCode(SqlTypes.JSON)
    private JsonNode background;
    private String title;


    @Getter
    @Transient
    private List<BaseEvent> uncommittedChanges = new ArrayList<>();

    public void applyEvent(BaseEvent event) {
        // Apply the event to the aggregate
        // Update state based on event type
        if (event instanceof ScreenCreatedEvent) {
            this.name = ((ScreenCreatedEvent) event).getName();
            this.data = ((ScreenCreatedEvent) event).getData();
            this.columns = ((ScreenCreatedEvent) event).getColumns();
            this.rowHeights = ((ScreenCreatedEvent) event).getRowHeights();
            this.rowMaxHeights = ((ScreenCreatedEvent) event).getRowMaxHeights();
            this.locals = ((ScreenCreatedEvent) event).getLocals();
            this.variableInitMapping = ((ScreenCreatedEvent) event).getVariableInitMapping();
            this.variableInit = ((ScreenCreatedEvent) event).getVariableInit();
            this.background = ((ScreenCreatedEvent) event).getBackground();
            this.title = ((ScreenCreatedEvent) event).getTitle();
        }

        // Add the event to uncommitted changes
        uncommittedChanges.add(event);
    }

    public void markChangesAsCommitted() {
        uncommittedChanges.clear();
    }

}
