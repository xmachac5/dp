package org.master.model.user;

import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.master.repository.user.UserRepository;

import java.util.UUID;

@ApplicationScoped
public class UserInitializer {

    @Inject
    UserRepository userRepository;

    @Transactional
    void onStart(@Observes StartupEvent ev){
        if (userRepository.count() == 0) {
            userRepository.persist(new User(UUID.randomUUID(), "admin", "admin@example.com",
                    "admin", "admin", "admin"));
        }
    }
}
