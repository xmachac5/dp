package org.master.command.screen.commands;

import org.master.command.Command;
import org.master.dto.screen.ScreenCreateDTO;
import org.master.dto.user.CreateUserDTO;

public class CreateScreenCommand implements Command {

    private final ScreenCreateDTO screenCreateDTO;

    public CreateScreenCommand(ScreenCreateDTO screenCreateDTO) {
        this.screenCreateDTO = screenCreateDTO;
    }

    public ScreenCreateDTO getScreenCreateDTO() {
        return screenCreateDTO;
    }
}
