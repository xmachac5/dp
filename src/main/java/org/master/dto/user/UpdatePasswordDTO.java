package org.master.dto.user;

import java.util.UUID;

public class UpdatePasswordDTO {
    private UUID uuid;
    private String newPassword;

    // Getters and setters
    public UUID getUserUuid() {
        return uuid;
    }

    public void setUserUuid(UUID userId) {
        this.uuid = userId;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
