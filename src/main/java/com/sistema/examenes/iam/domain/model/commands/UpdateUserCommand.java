package com.sistema.examenes.iam.domain.model.commands;

public record UpdateUserCommand(
        Long id,
        String username,
        String name,
        String lastname,
        String email,
        String phone
) {
    public UpdateUserCommand {
        if (id == null) {
            throw new IllegalArgumentException("id is required");
        }
        if (username == null) {
            throw new IllegalArgumentException("username is required");
        }
        if (name == null) {
            throw new IllegalArgumentException("name is required");
        }
        if (lastname == null) {
            throw new IllegalArgumentException("lastname is required");
        }
        if (email == null) {
            throw new IllegalArgumentException("email is required");
        }
        if (phone == null) {
            throw new IllegalArgumentException("phone is required");
        }
    }
}