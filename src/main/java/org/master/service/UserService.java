package org.master.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.master.model.User;

@ApplicationScoped
public class UserService {

    @Inject
    EntityManager em;

    @Transactional
    public void createUser(User user) {
        em.persist(user);
    }

    public User findById(Long id) {
        return em.find(User.class, id);
    }

    // Additional shared logic can be added here if needed
}
