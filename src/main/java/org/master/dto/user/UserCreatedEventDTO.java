package org.master.dto.user;

public class UserCreatedEventDTO {
    private String name;
    private String email;
    private String login;

    public UserCreatedEventDTO(String name, String email, String login) {
        this.name = name;
        this.email = email;
        this.login = login;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getLogin() {
        return login;
    }
}
