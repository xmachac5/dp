package org.master.model.screen;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "screens_read_model")
public class ScreenReadModel{

    @Id
    @Column(updatable = false, nullable = false, columnDefinition = "UUID")
    private UUID id ;

    @Column(columnDefinition = "json")
    @JdbcTypeCode(SqlTypes.JSON)
    private JsonNode data;

    private String name;

}
