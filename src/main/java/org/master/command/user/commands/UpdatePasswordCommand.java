package org.master.command.user.commands;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;


public record UpdatePasswordCommand (
        @NotNull
        UUID id,
        String newPassword
){}
