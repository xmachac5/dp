package org.master.command.process.task.commands;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record DeleteScreenTaskCommand(
        @NotNull UUID uuid

) {
}
