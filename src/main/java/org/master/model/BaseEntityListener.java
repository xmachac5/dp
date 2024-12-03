package org.master.model;


import jakarta.enterprise.inject.spi.CDI;
import jakarta.persistence.PrePersist;
import org.master.model.user.User;
import org.master.repository.user.UserRepository;


public class BaseEntityListener {

    @PrePersist
    public void setCreationFields(BaseEntity entity) {
        UserRepository userRepository = CDI.current().select(UserRepository.class).get();
        if (userRepository.isUserPresent()) {
            User currentUser = getCurrentUser();
            if (currentUser != null) {
                entity.setCreatedBy(currentUser);
            }
        }
    }

    private User getCurrentUser() {
        UserRepository userRepository = CDI.current().select(UserRepository.class).get();
        return userRepository.getCurrentUser();
    }
}
