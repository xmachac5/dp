package org.master.command.user.handler;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.master.command.user.commands.DeleteUserCommand;
import org.master.dto.user.CreateUserDTO;
import org.master.dto.user.UpdateUserDTO;
import org.master.dto.user.UserCreatedEventDTO;
import org.master.model.user.User;
import org.master.service.user.UserService;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

@ApplicationScoped
public class UserCommandHandler {

    @Inject
    UserService userService;

    @Inject
    @Channel("user-commands")  // Ensure this channel is configured in application.properties
    Emitter<String> userEmitter;

    @Inject
    ObjectMapper objectMapper; // Inject Jackson's ObjectMapper for JSON conversion

    public void handleCreateUserCommand(CreateUserDTO createUserDTO) {
        User user = new User();
        user.setName(createUserDTO.getName());
        user.setEmail(createUserDTO.getEmail());

        // Persist the user
        userService.createUser(user);

        // Emit the UserCreatedEventDTO to Kafka
        UserCreatedEventDTO eventDTO = new UserCreatedEventDTO(createUserDTO.getName(), createUserDTO.getEmail());
        emitEvent(eventDTO);
    }

    public void handleUpdateUserCommand(UpdateUserDTO userDTO) {
        userService.updateUser(userDTO.getId(), userDTO.getName(), userDTO.getEmail());
    }

    public void handleDeleteUserCommand(DeleteUserCommand command) {
        userService.deleteUser(command.getId());

        // Optionally emit a delete event
        // UserDeletedEventDTO eventDTO = new UserDeletedEventDTO(command.getId());
        // emitEvent(eventDTO);
    }

    private void emitEvent(Object eventDTO) {
        try {
            String eventJson = objectMapper.writeValueAsString(eventDTO);
            userEmitter.send(eventJson);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
