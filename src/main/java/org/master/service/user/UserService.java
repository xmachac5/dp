package org.master.service.user;

import io.quarkus.security.identity.SecurityIdentity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import org.master.dto.user.UserListDTO;
import org.master.model.user.User;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class UserService {

    @Inject
    EntityManager em;

    @Inject
    SecurityIdentity securityIdentity;

    public boolean isUserPresent() {
        Long count = em.createQuery("SELECT COUNT(u) FROM User u", Long.class).getSingleResult();
        return count > 0;
    }

    public User findByUuid(UUID uuid) {
        return em.find(User.class, uuid);
    }

    public List<UserListDTO> getAllUsers() {
        return em.createQuery(
                "SELECT new org.master.dto.user.UserListDTO(u.id, u.name, u.email) FROM User u", UserListDTO.class)
                .getResultList();
    }

    public User getCurrentUser(){
        return findByLogin(securityIdentity.getPrincipal().getName());
    }


    public User findByLogin(String login) {
        try {
            return em.createQuery("SELECT u FROM User u WHERE u.login = :login", User.class)
                    .setParameter("login", login)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null; // Handle case where no user is found
        }
    }


}
