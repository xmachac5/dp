package org.master.model.dataObject;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.master.model.BaseEntity;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "data_objects_column_write_model")
public class DataObjectsColumnWriteModel extends BaseEntity {
    private String name;
    private Boolean primaryKey;
    private String dataType;
    private Boolean isFk;
    private String description;
    @ManyToOne
    @JoinColumn(name = "dataObjectsWriteModelUuid", referencedColumnName = "id")
    private DataObjectsWriteModel dataObjectsWriteModelUuid;
    @ManyToOne
    @JoinColumn(name = "dataObjectsWriteModelForeignKeyUuid", referencedColumnName = "id")
    private DataObjectsWriteModel dataObjectsWriteModelForeignKeyUuid;

}
