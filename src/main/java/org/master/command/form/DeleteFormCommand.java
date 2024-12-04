package org.master.command.form;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record DeleteFormCommand (
        @NotNull UUID uuid
){}
