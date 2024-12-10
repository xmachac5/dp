package org.master.model.process.task;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.master.model.BaseEntity;
import org.master.model.process.ProcessVersionWriteModel;

@Getter
@Setter
@MappedSuperclass
public abstract class TaskWriteModel extends BaseEntity {

    private String name;
    @Column(columnDefinition = "json")
    @JdbcTypeCode(SqlTypes.JSON)
    private JsonNode variableMapping;
    @ManyToOne
    @JoinColumn(name = "processVersionWriteModel", referencedColumnName = "id")
    private ProcessVersionWriteModel processVersionWriteModel;
}
