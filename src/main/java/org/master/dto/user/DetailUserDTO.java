package org.master.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class DetailUserDTO {
    private UUID id;
    private String name;
    private String email;
    private String login;
    private String role;
    private UUID created_by;
    private LocalDateTime created_at;
    private UUID updated_by;
    private LocalDateTime updated_at;
    private UUID deleted_by;
    private LocalDateTime deleted_at;
}
