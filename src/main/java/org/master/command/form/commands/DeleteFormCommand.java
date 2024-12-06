package org.master.command.form.commands;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record DeleteFormCommand (
        @NotNull UUID uuid
){}
