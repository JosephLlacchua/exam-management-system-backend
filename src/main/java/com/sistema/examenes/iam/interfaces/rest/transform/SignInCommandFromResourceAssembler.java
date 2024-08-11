package com.sistema.examenes.iam.interfaces.rest.transform;

import com.sistema.examenes.iam.domain.model.commands.SignInCommand;
import com.sistema.examenes.iam.interfaces.rest.resources.SignInResource;

public class SignInCommandFromResourceAssembler {
    public static SignInCommand toCommandFromResource(SignInResource signInResource) {
        return new SignInCommand(signInResource.username(), signInResource.password());
    }
}
