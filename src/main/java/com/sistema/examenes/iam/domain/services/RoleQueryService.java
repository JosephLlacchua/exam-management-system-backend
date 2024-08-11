package com.sistema.examenes.iam.domain.services;


import com.sistema.examenes.iam.domain.model.entities.Role;
import com.sistema.examenes.iam.domain.model.queries.GetAllRolesQuery;
import com.sistema.examenes.iam.domain.model.queries.GetRoleByNameQuery;

import java.util.List;
import java.util.Optional;

public interface RoleQueryService {
    List<Role> handle(GetAllRolesQuery query);
    Optional<Role> handle(GetRoleByNameQuery query);
}
