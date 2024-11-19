package org.master.model.user;

import io.quarkus.security.jpa.Password;
import io.quarkus.security.jpa.Roles;
import io.quarkus.security.jpa.UserDefinition;
import io.quarkus.security.jpa.Username;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.master.model.BaseEntity;

@Entity
@Table(name = "app_user")
@UserDefinition
public class User extends BaseEntity {

    @NotBlank
    private String name;

    @NotBlank
    @Email
    private String email;

    @Column(unique = true)
    @Username
    private String login;

    @Password
    private String password;

    @Roles
    public String role;


    // Getters and Setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public @NotBlank String getLogin() {
        return login;
    }

    public void setLogin(@NotBlank String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public Object getRoles() {
        return null;
    }

    public void setRoles(String role) {
        this.role = role;
    }
}

