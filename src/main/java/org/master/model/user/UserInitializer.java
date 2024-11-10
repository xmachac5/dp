package org.master.model.user;

import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.master.dto.user.CreateUserDTO;
import org.master.service.user.UserService;

@ApplicationScoped
public class UserInitializer {

    @Inject
    UserService userService;

    @Transactional
    void onStart(@Observes StartupEvent ev){
        if (!userService.isUserPresent()) {
            CreateUserDTO firstUserDTO = new CreateUserDTO();
            firstUserDTO.setName("admin");
            firstUserDTO.setEmail("admin@example.com");
            firstUserDTO.setPassword("admin");
            firstUserDTO.setLogin("admin");
            firstUserDTO.setRole("admin");
            userService.createUser(firstUserDTO);
            System.out.println("First user created.");
        }
    }
}
