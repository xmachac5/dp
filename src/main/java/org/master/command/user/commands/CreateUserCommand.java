package org.master.command.user.commands;

public record CreateUserCommand (
        String name,
        String email,
        String login,
        String password,
        String role
){}
