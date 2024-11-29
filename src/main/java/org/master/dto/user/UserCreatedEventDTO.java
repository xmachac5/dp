package org.master.dto.user;

import lombok.Getter;

@Getter
public class UserCreatedEventDTO {
    // Getters
    private String name;
    private String email;
    private String login;

    public UserCreatedEventDTO(String name, String email, String login) {
        this.name = name;
        this.email = email;
        this.login = login;
    }

}
