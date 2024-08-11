package com.sistema.examenes.iam.application.internal.commandservices;

import com.sistema.examenes.iam.application.internal.outboundservices.hashing.HashingService;
import com.sistema.examenes.iam.application.internal.outboundservices.tokens.TokenService;
import com.sistema.examenes.iam.domain.model.aggregates.User;
import com.sistema.examenes.iam.domain.model.commands.DeleteUserCommand;
import com.sistema.examenes.iam.domain.model.commands.SignInCommand;
import com.sistema.examenes.iam.domain.model.commands.SignUpCommand;
import com.sistema.examenes.iam.domain.model.commands.UpdateUserCommand;
import com.sistema.examenes.iam.domain.services.UserCommandService;
import com.sistema.examenes.iam.infrastructure.persistence.jpa.repositories.RoleRepository;
import com.sistema.examenes.iam.infrastructure.persistence.jpa.repositories.UserRepository;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * User command service implementation
 * <p>
 *     This class implements the {@link UserCommandService} interface and provides the implementation for the
 *     {@link SignInCommand} and {@link SignUpCommand} commands.
 * </p>
 */
@Service
public class UserCommandServiceImpl implements UserCommandService {

    private final UserRepository userRepository;
    private final HashingService hashingService;
    private final TokenService tokenService;

    private final RoleRepository roleRepository;

    public UserCommandServiceImpl(UserRepository userRepository, HashingService hashingService, TokenService tokenService, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.hashingService = hashingService;
        this.tokenService = tokenService;
        this.roleRepository = roleRepository;
    }

    /**
     * Handle the sign-in command
     * <p>
     *     This method handles the {@link SignInCommand} command and returns the user and the token.
     * </p>
     * @param command the sign-in command containing the username and password
     * @return and optional containing the user matching the username and the generated token
     * @throws RuntimeException if the user is not found or the password is invalid
     */
    @Override
    public Optional<ImmutablePair<User, String>> handle(SignInCommand command) {
        var user = userRepository.findByUsername(command.username());
        if (user.isEmpty())
            throw new RuntimeException("User not found");
        if (!hashingService.matches(command.password(), user.get().getPassword()))
            throw new RuntimeException("Invalid password");
        var token = tokenService.generateToken(user.get().getUsername());
        return Optional.of(ImmutablePair.of(user.get(), token));
    }

    /**
     * Handle the sign-up command
     * <p>
     *     This method handles the {@link SignUpCommand} command and returns the user.
     * </p>
     * @param command the sign-up command containing the username and password
     * @return the created user
     */
    @Override
    public Optional<User> handle(SignUpCommand command) {
        if (userRepository.existsByUsername(command.username()))
            throw new RuntimeException("Username already exists");
        var roles = command.roles().stream().map(role -> roleRepository.findByName(role.getName()).orElseThrow(() -> new RuntimeException("Role name not found"))).toList();
        var user = new User(command.username(), hashingService.encode(command.password()), command.name(), command.lastname(), command.email(), command.phone(), roles);
        userRepository.save(user);
        return userRepository.findByUsername(command.username());
    }

    @Override
    public Optional<User> handle(UpdateUserCommand command) {
        Optional<User> userActual=userRepository.findById(command.id());
        if(userActual.isPresent()){
            User user=userActual.get();
            user.setUsername(command.username());
            user.setName(command.name());
            user.setLastname(command.lastname());
            user.setEmail(command.email());
            user.setPhone(command.phone());
            try{
                userRepository.save(user);
                return Optional.of(user);
            }catch (Exception e){
                throw new IllegalArgumentException("Error while updating user: " + e.getMessage());
            }
        }else {
            return Optional.empty();
        }
    }

    @Override
    public void handle(DeleteUserCommand command) {
        if(!userRepository .existsById(command.userId())) {
            throw new IllegalArgumentException("User does not exist");
        }
        else {
            try {
                userRepository.deleteById(command.userId());
            } catch (Exception e) {
                throw new IllegalArgumentException("Error while deleting user: " + e.getMessage());
            }
        }
    }
}
