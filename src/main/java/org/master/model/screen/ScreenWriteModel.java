package org.master.model.screen;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.master.model.BaseEntity;
import org.master.model.language.Language;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "screens_write_model")
public class ScreenWriteModel extends BaseEntity {

    @Column(columnDefinition = "json")
    @JdbcTypeCode(SqlTypes.JSON)
    private JsonNode data;
    private String name;
    private Integer columns;
    @Column(columnDefinition = "integer[]")
    @JdbcTypeCode(SqlTypes.ARRAY)
    private List<Integer> rowHeights;
    @ManyToOne
    @JoinColumn(name = "primaryLanguage", referencedColumnName = "id")
    private Language primaryLanguage;
    private String url;
    @Column(columnDefinition = "integer[]")
    @JdbcTypeCode(SqlTypes.ARRAY)
    private List<Integer> rowMaxHeights;
    @Column(columnDefinition = "json")
    @JdbcTypeCode(SqlTypes.JSON)
    private JsonNode locals;
    @Column(columnDefinition = "json")
    @JdbcTypeCode(SqlTypes.JSON)
    private JsonNode variableInit = JsonNodeFactory.instance.objectNode();
    @Column(columnDefinition = "json")
    @JdbcTypeCode(SqlTypes.JSON)
    private JsonNode variableInitMapping = JsonNodeFactory.instance.objectNode();
    @Column(columnDefinition = "json")
    @JdbcTypeCode(SqlTypes.JSON)
    private JsonNode background;
    private String title;

}
