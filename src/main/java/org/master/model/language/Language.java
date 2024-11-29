package org.master.model.language;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.master.model.BaseEntity;

import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "languages")
public class Language extends BaseEntity {

    private String name;
    private String code;
    private Boolean allowed;

    public Language(UUID id, String name, String code, Boolean allowed) {
        this.setId(id);
        this.name = name;
        this.code = code;
        this.allowed = allowed;
    }
}
