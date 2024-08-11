package com.sistema.examenes.iam.interfaces.rest.transform;

import com.sistema.examenes.iam.domain.model.entities.Role;
import com.sistema.examenes.iam.interfaces.rest.resources.RoleResource;

public class RoleResourceFromEntityAssembler {
    public static RoleResource toResourceFromEntity(Role role) {
        return new RoleResource(role.getId(), role.getStringName());
    }
}
