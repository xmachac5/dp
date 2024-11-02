package org.master.command.user.commands;

import org.master.command.Command;
import org.master.dto.user.CreateUserDTO;

public class CreateUserCommand implements Command {
    private final CreateUserDTO userDTO;

    public CreateUserCommand(CreateUserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public CreateUserDTO getUserDTO() {
        return userDTO;
    }
}
