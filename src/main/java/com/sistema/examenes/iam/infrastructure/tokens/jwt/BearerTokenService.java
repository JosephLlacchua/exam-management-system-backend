package com.sistema.examenes.iam.infrastructure.tokens.jwt;

import com.sistema.examenes.iam.application.internal.outboundservices.tokens.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;

/**
 * This interface is a marker interface for the JWT token service.
 * It extends the {@link TokenService} interface.
 * This interface is used to inject the JWT token service in the {@link com.acme.learning.platform.iam.infrastructure.tokens.jwt.services.TokenServiceImpl} class.
 */
public interface BearerTokenService extends TokenService {

    /**
     * This method is responsible for extracting the JWT token from the HTTP request.
     * @param token the HTTP request
     * @return String the JWT token
     */
    String getBearerTokenFrom(HttpServletRequest token);

    /**
     * This method is responsible for generating a JWT token from an authentication object.
     * @param authentication the authentication object
     * @return String the JWT token
     * @see Authentication
     */
    String generateToken(Authentication authentication);
}
