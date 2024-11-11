package org.master.model;

import jakarta.enterprise.inject.spi.CDI;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import org.master.model.user.User;
import org.master.service.user.UserService;

import java.time.LocalDateTime;

public class BaseEntityListener {

    @PrePersist
    public void setCreationFields(BaseEntity entity) {
        UserService userService = CDI.current().select(UserService.class).get();
        if (userService.isUserPresent()) {
            User currentUser = getCurrentUser();
            if (currentUser != null) {
                entity.setCreatedBy(currentUser);
            }
        }
    }

/**    @PreUpdate
    public void setUpdateFields(BaseEntity entity) {
        User currentUser = getCurrentUser();
        if (currentUser != null && entity.getUpdatedBy() != currentUser) {
            entity.setUpdatedBy(currentUser);
            entity.setUpdatedAt(LocalDateTime.now());
        }
    }**/

    private User getCurrentUser() {
        UserService userService = CDI.current().select(UserService.class).get();
        return userService.getCurrentUser();
    }
}
