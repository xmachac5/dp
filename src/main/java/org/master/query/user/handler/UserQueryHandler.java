package org.master.query.user.handler;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.master.model.user.User;
import org.master.service.user.UserService;

import java.util.List;

@ApplicationScoped
public class UserQueryHandler {
    @Inject
    UserService userService;

    public User getUserById(Long id) {
        return userService.findById(id);
    }

    public List<User> getUserList() {
        return userService.getAllUsers();
    }
}
