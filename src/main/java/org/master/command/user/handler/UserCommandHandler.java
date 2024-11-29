package org.master.command.user.handler;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.master.command.user.commands.CreateUserCommand;
import org.master.command.user.commands.DeleteUserCommand;
import org.master.command.user.commands.UpdatePasswordCommand;
import org.master.command.user.commands.UpdateUserCommand;
import org.master.model.user.User;
import org.master.repository.user.UserRepository;
import org.master.service.user.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ApplicationScoped
public class UserCommandHandler {

    @Inject
    UserRepository userRepository;

    @Inject
    ObjectMapper objectMapper; // Inject Jackson's ObjectMapper for JSON conversion

    public void handleCreateUserCommand(CreateUserCommand createUserCommand){

        if (userRepository.findByLogin(createUserCommand.login()) != null) {
            throw new RuntimeException("Login already exists: " + createUserCommand.login());
        }

        // Create the user
        userRepository.createUser(createUserCommand);
    }

    public void handleUpdateUserCommand(UpdateUserCommand updateUserCommand) {
        userRepository.updateUser(updateUserCommand.id(), updateUserCommand.name(), updateUserCommand.email());
    }

    public void handleDeleteUserCommand(DeleteUserCommand command) {
        userRepository.deleteUser(command.id());

    }

    public void handleUpdatePasswordCommand(UpdatePasswordCommand command){
        User user = userRepository.findByUuid(command.id());
        if (user != null) {
            userRepository.updatePassword(user, command.newPassword());
        } else {
            throw new IllegalArgumentException("User not found with ID: " + command.id());
        }
    }
}
