package org.master.command.language.commands;

import lombok.Getter;
import java.util.UUID;

@Getter
public class UpdateAllowedCommand {

    private UUID id;
    private Boolean allowed;

    public UpdateAllowedCommand(UUID id, Boolean allowed) {
        this.id = id;
        this.allowed = allowed;
    }
}