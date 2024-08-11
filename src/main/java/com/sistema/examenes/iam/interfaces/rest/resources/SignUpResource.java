package com.sistema.examenes.iam.interfaces.rest.resources;

import java.util.List;

public record SignUpResource(String username, String password, String name, String lastname, String email, String phone, List<String> roles) {
}