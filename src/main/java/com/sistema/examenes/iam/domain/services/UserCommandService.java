package com.sistema.examenes.iam.domain.services;


import com.sistema.examenes.iam.domain.model.aggregates.User;
import com.sistema.examenes.iam.domain.model.commands.DeleteUserCommand;
import com.sistema.examenes.iam.domain.model.commands.SignInCommand;
import com.sistema.examenes.iam.domain.model.commands.SignUpCommand;
import com.sistema.examenes.iam.domain.model.commands.UpdateUserCommand;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.Optional;

public interface UserCommandService {
    Optional<ImmutablePair<User, String>> handle(SignInCommand command);
    Optional<User> handle(SignUpCommand command);
    Optional<User> handle(UpdateUserCommand command);
    void handle(DeleteUserCommand command);

}
