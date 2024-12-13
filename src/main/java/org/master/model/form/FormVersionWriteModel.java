package org.master.model.form;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.master.model.BaseEntity;
import org.master.model.dataObject.DataObjectsWriteModel;
import org.master.model.language.Language;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "forms_version_definitions_write_model")
public class FormVersionWriteModel extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "formWriteModel", referencedColumnName = "id")
    private FormWriteModel formWriteModel;
    @Column(columnDefinition = "json")
    @JdbcTypeCode(SqlTypes.JSON)
    private JsonNode definitions;
    @Column(columnDefinition = "json")
    @JdbcTypeCode(SqlTypes.JSON)
    private JsonNode columnMapping;
    private Integer columns;
    @Column(columnDefinition = "integer[]")
    @JdbcTypeCode(SqlTypes.ARRAY)
    private List<Integer> rowHeights;
    @ManyToOne
    @JoinColumn(name = "primaryLanguage", referencedColumnName = "id")
    private Language primaryLanguage;
    @Column(columnDefinition = "integer[]")
    @JdbcTypeCode(SqlTypes.ARRAY)
    private List<Integer> rowMaxHeights;
    private Integer version;
    @OneToOne
    @JoinColumn(name = "dataObjectsWriteModel", referencedColumnName = "id")
    private DataObjectsWriteModel dataObjectsWriteModel;
}
