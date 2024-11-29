package org.master.command.user.commands;

import java.util.UUID;

public record UpdateUserCommand(
        UUID id,
        String name,
        String email
){}

