package org.master.dto.user;

public class UserCreatedEventDTO {
    private String name;
    private String email;

    public UserCreatedEventDTO(String name, String email) {
        this.name = name;
        this.email = email;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
