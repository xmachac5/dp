package org.master.command.process.commands;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ConceptProcessCommand(
        @NotNull UUID uuid
) {
}
