package com.sistema.examenes.iam.interfaces.rest.resources;

import java.util.List;

public record UserResource(Long id, String username, String name, String lastname, String email, String phone, List<String> roles) {
}