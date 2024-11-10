package org.master.service.user;

import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;
import org.master.dto.user.CreateUserDTO;
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

    public void createUser(CreateUserDTO createUserDTO){

        User newUser = new User();
        newUser.setName(createUserDTO.getName());
        newUser.setEmail(createUserDTO.getEmail());
        newUser.setLogin(createUserDTO.getLogin());
        newUser.setPassword(BcryptUtil.bcryptHash(createUserDTO.getPassword()));
        newUser.setRoles(createUserDTO.getRole());

        // Set createdBy to null explicitly for the first user
        if (!isUserPresent()) {
            newUser.setCreatedBy(null);
        }

        em.persist(newUser);
    }

    public User findByUuid(UUID uuid) {
        return em.find(User.class, uuid);
    }

    @Transactional
    public void updateUser(UUID uuid, String name, String email) {
        User user = em.find(User.class, uuid);
        if (user != null) {
            user.setName(name);
            user.setEmail(email);
        }
        em.merge(user);
    }

    @Transactional
    public void updatePassword(User user, String newPassword) {

        user.setPassword(BcryptUtil.bcryptHash(newPassword));
    }

    @Transactional
    public void deleteUser(UUID uuid) {
        User user = em.find(User.class, uuid);
        if (user != null) {
            em.remove(user);
        }
    }

    public List<UserListDTO> getAllUsers() {
        return em.createQuery(
                "SELECT new org.master.dto.user.UserListDTO(u.id, u.name, u.email) FROM User u", UserListDTO.class)
                .getResultList();
    }

    public User getCurrentUser(){
        return findByLogin(securityIdentity.getPrincipal().getName());
    }


    private User findByLogin(String login) {
        try {
            return em.createQuery("SELECT u FROM User u WHERE u.login = :login", User.class)
                    .setParameter("login", login)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null; // Handle case where no user is found
        }
    }


}
