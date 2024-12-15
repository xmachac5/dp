package org.master.command.dataObject.commands;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record PublishDataObjectCommand(
        @NotNull UUID id
) {
}
