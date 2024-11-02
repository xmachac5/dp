package org.master.service.user;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.master.model.user.User;

import java.util.List;

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

    @Transactional
    public void updateUser(Long id, String name, String email) {
        User user = em.find(User.class, id);
        if (user != null) {
            user.setName(name);
            user.setEmail(email);
        }
    }

    @Transactional
    public void deleteUser(Long id) {
        User user = em.find(User.class, id);
        if (user != null) {
            em.remove(user);
        }
    }

    public List<User> getAllUsers() {
        return em.createQuery("SELECT u FROM User u", User.class)
            .getResultList();
    }
}
