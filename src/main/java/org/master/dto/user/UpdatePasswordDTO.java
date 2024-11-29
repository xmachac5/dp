package org.master.dto.user;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
public class UpdatePasswordDTO {
    // Getters and setters
    private UUID uuid;
    @Setter
    private String newPassword;

    public void setUUuid(UUID userId) {
        this.uuid = userId;
    }

}
