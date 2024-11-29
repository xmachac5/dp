package org.master.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class UpdateUserDTO {
    // Getters and setters
    @NotNull
    private UUID uuid;

    @NotBlank
    private String name;

    @NotBlank
    @Email
    private String email;

}
