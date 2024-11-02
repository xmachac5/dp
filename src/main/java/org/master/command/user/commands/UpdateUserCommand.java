package org.master.command.user.commands;

import org.master.command.Command;
import org.master.dto.user.UpdateUserDTO;

public class UpdateUserCommand implements Command {
    private final UpdateUserDTO userDTO;

    public UpdateUserCommand(UpdateUserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public UpdateUserDTO getUserDTO() {
        return userDTO;
    }
}
