package com.sistema.examenes.iam.interfaces.rest.transform;

import com.sistema.examenes.iam.domain.model.aggregates.User;
import com.sistema.examenes.iam.domain.model.entities.Role;
import com.sistema.examenes.iam.interfaces.rest.resources.UserResource;

public class UserResourceFromEntityAssembler {
    public static UserResource toResourceFromEntity(User user) {
        var roles = user.getRoles().stream().map(Role::getStringName).toList();
        return new UserResource(user.getId(), user.getUsername(), user.getName(), user.getLastname(), user.getEmail(), user.getPhone(), roles);
    }
}