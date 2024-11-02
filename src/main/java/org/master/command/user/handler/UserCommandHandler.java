package org.master.command.user.handler;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.master.command.user.commands.DeleteUserCommand;
import org.master.command.user.commands.UpdateUserCommand;
import org.master.model.user.User;
import org.master.service.user.UserService;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

@ApplicationScoped
public class UserCommandHandler {

    @Inject
    UserService userService;

    @Inject
    @Channel("user-commands")
    Emitter<String> userEmitter;

    public void handleCreateUserCommand(String name, String email) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);

        // Use UserService to persist the user
        userService.createUser(user);

        // Emit an event to Kafka
        userEmitter.send("User created: " + name);
    }
    public void handleUpdateUserCommand(UpdateUserCommand command) {
        userService.updateUser(command.getId(), command.getName(), command.getEmail());
    }

    public void handleDeleteUserCommand(DeleteUserCommand command) {
        userService.deleteUser(command.getId());
    }
}
