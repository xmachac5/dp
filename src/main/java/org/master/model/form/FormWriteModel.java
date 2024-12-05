package org.master.model.form;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.master.model.BaseEntity;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "forms_definitions_write_model")
public class FormWriteModel extends BaseEntity {

    private String name;
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "latestVersionUuid", referencedColumnName = "id")
    private FormVersionWriteModel latestVersionUuid;

}
