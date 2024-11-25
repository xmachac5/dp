package org.master.command.screen.commands;

import lombok.Getter;
import org.master.command.Command;
import org.master.dto.screen.ScreenCreateDTO;
import org.master.dto.user.CreateUserDTO;

@Getter
public class CreateScreenCommand implements Command {

    private final ScreenCreateDTO screenCreateDTO;

    public CreateScreenCommand(ScreenCreateDTO screenCreateDTO) {
        this.screenCreateDTO = screenCreateDTO;
    }

}
