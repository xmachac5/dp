package org.master.model.form;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.master.model.language.Language;

import java.util.List;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "forms_definitions_read_model")
public class FormReadModel {
    @Id
    @Column(updatable = false, nullable = false, columnDefinition = "UUID")
    private UUID id ;
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
}
