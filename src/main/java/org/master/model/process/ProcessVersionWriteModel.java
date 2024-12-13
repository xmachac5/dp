package org.master.model.process;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.master.model.BaseEntity;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "processes_version_definitions_write_model")
public class ProcessVersionWriteModel extends BaseEntity {
    private Boolean published;
    @ManyToOne
    @JoinColumn(name = "processWriteModel", referencedColumnName = "id")
    private ProcessWriteModel processWriteModel;
    private Integer Version;
    @Column(columnDefinition = "json")
    @JdbcTypeCode(SqlTypes.JSON)
    private JsonNode variables;
}
