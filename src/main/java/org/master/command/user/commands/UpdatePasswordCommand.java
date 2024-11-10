package org.master.command.user.commands;

import java.util.UUID;

public class UpdatePasswordCommand {
    private UUID uuid;
    private String newPassword;

    // Constructor, Getters, Setters
    public UpdatePasswordCommand(UUID uuid, String newPassword) {
        this.uuid = uuid;
        this.newPassword = newPassword;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getNewPassword() {
        return newPassword;
    }
}
