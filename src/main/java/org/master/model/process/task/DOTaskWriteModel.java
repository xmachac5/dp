package org.master.model.process.task;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.master.model.dataObject.DataObjectsWriteModel;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "data_object_tasks_write_model")
public class DOTaskWriteModel extends TaskWriteModel {
    @ManyToOne
    @JoinColumn(name = "dataObjectsWriteModel", referencedColumnName = "id")
    private DataObjectsWriteModel dataObjectsWriteModel;
    @Column(columnDefinition = "json")
    @JdbcTypeCode(SqlTypes.JSON)
    private JsonNode columnsMapping;
}
