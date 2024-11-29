package org.master.model.user;

import io.quarkus.security.jpa.Password;
import io.quarkus.security.jpa.Roles;
import io.quarkus.security.jpa.UserDefinition;
import io.quarkus.security.jpa.Username;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.master.model.BaseEntity;

@Entity
@Table(name = "app_user")
@UserDefinition
public class User extends BaseEntity {

    @Setter
    @Getter
    @NotBlank
    private String name;

    @Setter
    @Getter
    @NotBlank
    @Email
    private String email;

    @Column(unique = true)
    @Username
    private String login;

    @Setter
    @Password
    private String password;

    @Roles
    public String role;


    // Getters and Setters

    public @NotBlank String getLogin() {
        return login;
    }

    public void setLogin(@NotBlank String login) {
        this.login = login;
    }


    public Object getRoles() {
        return null;
    }

    public void setRoles(String role) {
        this.role = role;
    }
}

