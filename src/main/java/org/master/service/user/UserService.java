package org.master.service.user;

import io.quarkus.security.identity.SecurityIdentity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import org.master.dto.user.DetailUserDTO;
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

    public DetailUserDTO findByUuid(UUID uuid) {
        try {
            return em.createQuery(
                            "SELECT new org.master.dto.user.DetailUserDTO(u.id, u.name, u.email, u.login," +
                                    "u.role, u.createdBy.id, u.createdAt, u.updatedBy.id, u.updatedAt, u.deletedBy.id," +
                                    "u.deletedAt) " +
                                    "FROM User u LEFT JOIN u.createdBy LEFT JOIN u.updatedBy LEFT JOIN u.createdBy" +
                                    " WHERE u.id = :uuid", DetailUserDTO.class)
                    .setParameter("uuid", uuid)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null; // Handle case where no user is found
        }
    }

    public List<UserListDTO> getAllUsers() {
        return em.createQuery(
                "SELECT new org.master.dto.user.UserListDTO(u.id, u.name, u.email) FROM User u", UserListDTO.class)
                .getResultList();
    }

    public DetailUserDTO getCurrentUser(){
        return findByLogin(securityIdentity.getPrincipal().getName());
    }


    public DetailUserDTO findByLogin(String login) {
        try {
            return em.createQuery("SELECT new org.master.dto.user.DetailUserDTO(u.id, u.name, u.email, u.login," +
                            "u.role, u.createdBy.id, u.createdAt, u.updatedBy.id, u.updatedAt, u.deletedBy.id," +
                            "u.deletedAt) " +
                            "FROM User u LEFT JOIN u.createdBy LEFT JOIN u.updatedBy LEFT JOIN u.createdBy" +
                            " WHERE u.login = :login", DetailUserDTO.class)
                    .setParameter("login", login)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null; // Handle case where no user is found
        }
    }


}
