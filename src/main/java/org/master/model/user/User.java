package org.master.model.user;

import io.quarkus.security.jpa.Password;
import io.quarkus.security.jpa.Roles;
import io.quarkus.security.jpa.UserDefinition;
import io.quarkus.security.jpa.Username;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.master.model.BaseEntity;

import java.util.UUID;

@Setter
@NoArgsConstructor
@Entity
@Table(name = "app_user")
@UserDefinition
public class User extends BaseEntity {

    @Getter
    @NotBlank
    private String name;

    @Getter
    @NotBlank
    @Email
    private String email;

    @Getter
    @Column(unique = true)
    @Username
    private String login;

    @Password
    private String password;

    @Getter
    @Roles
    public String role;

    public User(UUID id, String name, String email, String login, String password, String role) {
        this.setId(id);
        this.setName(name);
        this.setEmail(email);
        this.setLogin(login);
        this.setPassword(password);
        this.setRole(role);
    }


}

