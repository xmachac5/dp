package org.master.command.user.handler;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.master.command.user.commands.DeleteUserCommand;
import org.master.command.user.commands.UpdatePasswordCommand;
import org.master.dto.user.CreateUserDTO;
import org.master.dto.user.UpdateUserDTO;
import org.master.dto.user.UserCreatedEventDTO;
import org.master.model.user.User;
import org.master.service.user.UserService;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@ApplicationScoped
public class UserCommandHandler {

    @Inject
    UserService userService;

    @Inject
    @Channel("user-commands")  // Ensure this channel is configured in application.properties
    Emitter<String> userEmitter;

    @Inject
    ObjectMapper objectMapper; // Inject Jackson's ObjectMapper for JSON conversion

    @Transactional
    public void handleCreateUserCommand(CreateUserDTO createUserDTO){

        // Create the user
        userService.createUser(createUserDTO);

        // Emit the UserCreatedEventDTO to Kafka
        UserCreatedEventDTO eventDTO = new UserCreatedEventDTO(createUserDTO.getName(), createUserDTO.getEmail(),
                createUserDTO.getLogin());
        emitEvent(eventDTO);
    }

    public void handleUpdateUserCommand(UpdateUserDTO userDTO) {
        userService.updateUser(userDTO.getUuid(), userDTO.getName(), userDTO.getEmail());
    }

    public void handleDeleteUserCommand(DeleteUserCommand command) {
        userService.deleteUser(command.getUuid());

        // Optionally emit a delete event
        // UserDeletedEventDTO eventDTO = new UserDeletedEventDTO(command.getId());
        // emitEvent(eventDTO);
    }

    @Transactional
    public void handleUpdatePasswordCommand(UpdatePasswordCommand command){
        User user = userService.findByUuid(command.getUuid());
        if (user != null) {
            userService.updatePassword(user, command.getNewPassword());
        } else {
            throw new IllegalArgumentException("User not found with ID: " + command.getUuid());
        }
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
