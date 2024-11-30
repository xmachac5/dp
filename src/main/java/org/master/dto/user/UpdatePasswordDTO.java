package org.master.dto.user;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
public class UpdatePasswordDTO {

    private UUID uuid;
    @Setter
    private String newPassword;

}
