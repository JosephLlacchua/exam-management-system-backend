package com.sistema.examenes.iam.interfaces.rest.transform;

import com.sistema.examenes.iam.domain.model.aggregates.User;
import com.sistema.examenes.iam.interfaces.rest.resources.AuthenticatedUserResource;

public class AuthenticatedUserResourceFromEntityAssembler {
    public static AuthenticatedUserResource toResourceFromEntity(User user, String token) {
        return new AuthenticatedUserResource(user.getId(), user.getUsername(), token);
    }
}
