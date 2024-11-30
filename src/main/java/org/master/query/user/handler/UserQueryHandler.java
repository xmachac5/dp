package org.master.query.user.handler;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.master.dto.user.DetailUserDTO;
import org.master.dto.user.UserListDTO;
import org.master.model.user.User;
import org.master.service.user.UserService;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class UserQueryHandler {
    @Inject
    UserService userService;

    public DetailUserDTO getUserByUuid(UUID uuid) {
        return userService.findByUuid(uuid);
    }

    public List<UserListDTO> getUserList() {
        return userService.getAllUsers();
    }

    public DetailUserDTO getCurrentUser() { return userService.getCurrentUser(); }
}
