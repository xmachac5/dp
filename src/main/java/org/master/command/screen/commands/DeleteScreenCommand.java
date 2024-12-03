package org.master.command.screen.commands;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record DeleteScreenCommand (
        @NotNull UUID uuid
){}
