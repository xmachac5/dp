package org.master.command.script.commands;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record PublishScriptCommand(
        @NotNull UUID id
) {
}
