package org.master.command.dataObject;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record DeleteDataObjectCommand (
        @NotNull UUID uuid
){}
