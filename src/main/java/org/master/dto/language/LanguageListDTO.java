package org.master.dto.language;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class LanguageListDTO {

    private UUID id;
    private String code;
    private Boolean allowed;

    public LanguageListDTO(UUID id, String code, Boolean allowed) {
        this.id = id;
        this.code = code;
        this.allowed = allowed;
    }
}
