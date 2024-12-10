package org.master.model.process.task;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.master.model.process.ProcessReadModel;

import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "tasks_read_model")
public class TaskReadModel {
    @Id
    @Column(updatable = false, nullable = false, columnDefinition = "UUID")
    private UUID id ;
    private TaskType type;
    private UUID targetEntityUuid;
    @Column(columnDefinition = "json")
    @JdbcTypeCode(SqlTypes.JSON)
    private JsonNode variableMapping;
    @ManyToOne
    @JoinColumn(name = "processReadModel", referencedColumnName = "id")
    private ProcessReadModel processReadModel;
    @Column(columnDefinition = "json")
    @JdbcTypeCode(SqlTypes.JSON)
    private JsonNode columnsMapping;
}
