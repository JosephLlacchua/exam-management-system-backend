package com.sistema.examenes.iam.domain.model.commands;



import com.sistema.examenes.iam.domain.model.entities.Role;

import java.util.List;

public record SignUpCommand(String username, String password,String name ,String lastname, String email, String phone,List<Role> roles) {
}