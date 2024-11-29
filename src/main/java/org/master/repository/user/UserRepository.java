package org.master.repository.user;

import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;
import org.master.command.user.commands.CreateUserCommand;
import org.master.model.user.User;

import java.time.LocalDateTime;
import java.util.UUID;

@ApplicationScoped
public class UserRepository implements PanacheRepository<User> {

    @Inject
    EntityManager em;

    @Inject
    SecurityIdentity securityIdentity;

    @Transactional
    public void createUser(CreateUserCommand createUserCommand){

        User newUser = new User();
        newUser.setId(UUID.randomUUID());
        newUser.setName(createUserCommand.name());
        newUser.setEmail(createUserCommand.email());
        newUser.setLogin(createUserCommand.login());
        newUser.setPassword(BcryptUtil.bcryptHash(createUserCommand.password()));
        newUser.setRole(createUserCommand.role());

        // Set createdBy to null explicitly for the first user
        if (!isUserPresent()) {
            newUser.setCreatedBy(null);
        }

        em.persist(newUser);
    }

    @Transactional
    public void updateUser(UUID uuid, String name, String email) {
        User user = em.find(User.class, uuid);
        if (user != null) {
            user.setName(name);
            user.setEmail(email);
            user.setUpdatedAt(LocalDateTime.now());
            user.setUpdatedBy(getCurrentUser());
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

    public User findByLogin(String login) {
        try {
            return em.createQuery("SELECT u FROM User u WHERE u.login = :login", User.class)
                    .setParameter("login", login)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null; // Handle case where no user is found
        }
    }

    public boolean isUserPresent() {
        Long count = em.createQuery("SELECT COUNT(u) FROM User u", Long.class).getSingleResult();
        return count > 0;
    }

    public User getCurrentUser(){
        return findByLogin(securityIdentity.getPrincipal().getName());
    }

    public User findByUuid(UUID uuid) {
        return em.find(User.class, uuid);
    }

}
