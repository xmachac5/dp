package org.master.query.handler;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.master.model.User;
import org.master.service.UserService;

@ApplicationScoped
public class UserQueryHandler {
    @Inject
    UserService userService;

    public User getUserById(Long id) {
        return userService.findById(id);
    }
}
