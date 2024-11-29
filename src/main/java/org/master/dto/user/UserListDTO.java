package org.master.dto.user;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class UserListDTO {
    private UUID id;
    private String name;
    private String email;
    // Other fields you want to expose

    // Constructor to initialize fields
    public UserListDTO(UUID id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

}
