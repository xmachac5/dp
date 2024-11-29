package org.master.model.language;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.master.model.BaseEntity;

@Setter
@Getter
@Entity
@Table(name = "languages")
public class Language extends BaseEntity {

    private String name;
    private String code;
    private Boolean allowed;

    public Language() {}

    public Language(String name, String code, Boolean allowed) {
        this.name = name;
        this.code = code;
        this.allowed = allowed;
    }
}
