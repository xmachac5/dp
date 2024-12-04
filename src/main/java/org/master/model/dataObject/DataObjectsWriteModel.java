package org.master.model.dataObject;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.master.model.BaseEntity;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "data_objects_write_model")
public class DataObjectsWriteModel extends BaseEntity {
    private String name;
    private Integer version;
    private String description;
    private Boolean trackChanges;
    private Boolean softDelete;
}
