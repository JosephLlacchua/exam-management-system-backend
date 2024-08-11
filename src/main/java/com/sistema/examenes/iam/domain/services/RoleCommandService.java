package com.sistema.examenes.iam.domain.services;

import com.sistema.examenes.iam.domain.model.commands.SeedRolesCommand;

public interface RoleCommandService {
    void handle(SeedRolesCommand command);
}
