package org.master.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
public class UserListDTO {
    private UUID id;
    private String name;
    private String email;

}
