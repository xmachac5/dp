package org.master.model.process;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.master.model.BaseEntity;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "processes_write_model")
public class ProcessWriteModel extends BaseEntity {
    private String name;
    @OneToOne
    @JoinColumn(name = "processVersionWriteModel", referencedColumnName = "id")
    private ProcessVersionWriteModel processVersionWriteModel;
}
