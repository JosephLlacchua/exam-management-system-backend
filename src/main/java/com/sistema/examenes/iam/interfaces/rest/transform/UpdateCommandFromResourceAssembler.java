package com.sistema.examenes.iam.interfaces.rest.transform;

import com.sistema.examenes.iam.domain.model.commands.UpdateUserCommand;
import com.sistema.examenes.iam.interfaces.rest.resources.UpdateUserResource;

public class UpdateCommandFromResourceAssembler {
    public static UpdateUserCommand toCommandFromResource(Long id , UpdateUserResource resource) {
        return new UpdateUserCommand(id, resource.username(), resource.name(), resource.lastname(), resource.email(), resource.phone());
    }
}