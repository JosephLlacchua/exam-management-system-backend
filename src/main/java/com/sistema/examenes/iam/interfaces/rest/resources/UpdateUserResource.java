package com.sistema.examenes.iam.interfaces.rest.resources;

public record UpdateUserResource(
        String username,
        String name,
        String lastname,
        String email,
        String phone
) {
}
