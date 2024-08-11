package com.sistema.examenes.iam.infrastructure.tokens.jwt.services;

import com.sistema.examenes.iam.infrastructure.tokens.jwt.BearerTokenService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;

/**
 * Service implementation for handling JSON Web Tokens (JWT).
 * Implementación del servicio para manejar tokens JSON Web (JWT).
 *
 * This class provides methods for generating, validating, and extracting
 * information from JWT tokens. It utilizes the secret key and expiration
 * days defined in the application properties.
 *
 * Esta clase proporciona métodos para generar, validar y extraer
 * información de los tokens JWT. Utiliza la clave secreta y los días de
 * expiración definidos en las propiedades de la aplicación.
 */
@Service
public class TokenServiceImpl implements BearerTokenService {
    private final Logger LOGGER = LoggerFactory.getLogger(TokenServiceImpl.class);

    private static final String AUTHORIZATION_PARAMETER_NAME = "Authorization";
    private static final String BEARER_TOKEN_PREFIX = "Bearer ";
    private static final int TOKEN_BEGIN_INDEX = 7;

    @Value("${authorization.jwt.secret}")
    private String secret;

    @Value("${authorization.jwt.expiration.days}")
    private int expirationDays;

    /**
     * Generates a JWT token using the provided authentication object.
     * Genera un token JWT utilizando el objeto de autenticación proporcionado.
     *
     * @param authentication the authentication object containing user details
     *                       el objeto de autenticación que contiene detalles del usuario
     * @return a JWT token as a String
     *         un token JWT como una cadena
     * @see Authentication
     */
    @Override
    public String generateToken(Authentication authentication) {
        return buildTokenWithDefaultParameters(authentication.getName());
    }

    /**
     * Generates a JWT token for the given username.
     * Genera un token JWT para el nombre de usuario proporcionado.
     *
     * @param username the username for which the token is to be generated
     *                 el nombre de usuario para el cual se generará el token
     * @return a JWT token as a String
     *         un token JWT como una cadena
     */
    public String generateToken(String username) {
        return buildTokenWithDefaultParameters(username);
    }

    /**
     * Builds a JWT token with the default expiration time using the provided username.
     * Construye un token JWT con el tiempo de expiración predeterminado usando el nombre de usuario proporcionado.
     *
     * @param username the username to be included in the token
     *                 el nombre de usuario que se incluirá en el token
     * @return a JWT token as a String
     *         un token JWT como una cadena
     */
    private String buildTokenWithDefaultParameters(String username) {
        var issuedAt = new Date();
        var expiration = DateUtils.addDays(issuedAt, expirationDays);
        var key = getSigningKey();
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(issuedAt)
                .setExpiration(expiration)
                .signWith(key)
                .compact();
    }

    /**
     * Extracts the username from the given JWT token.
     * Extrae el nombre de usuario del token JWT proporcionado.
     *
     * @param token the JWT token from which to extract the username
     *              el token JWT del cual extraer el nombre de usuario
     * @return the username as a String
     *         el nombre de usuario como una cadena
     */
    @Override
    public String getUsernameFromToken(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Validates the given JWT token.
     * Valida el token JWT proporcionado.
     *
     * @param token the JWT token to be validated
     *              el token JWT que se va a validar
     * @return true if the token is valid, false otherwise
     *         verdadero si el token es válido, falso en caso contrario
     */
    @Override
    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token);
            LOGGER.info("Token is valid");
            return true;
        } catch (SignatureException e) {
            LOGGER.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            LOGGER.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            LOGGER.error("Expired JWT token: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            LOGGER.error("Unsupported JWT token: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            LOGGER.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }

    /**
     * Extracts a specific claim from the given JWT token.
     * Extrae un reclamo específico del token JWT proporcionado.
     *
     * @param <T> the type of the claim
     *            el tipo del reclamo
     * @param token the JWT token from which to extract the claim
     *              el token JWT del cual extraer el reclamo
     * @param claimsResolver a function to extract the claim from the Claims object
     *                       una función para extraer el reclamo del objeto Claims
     * @return the extracted claim
     *         el reclamo extraído
     */
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Extracts all claims from the given JWT token.
     * Extrae todos los reclamos del token JWT proporcionado.
     *
     * @param token the JWT token from which to extract the claims
     *              el token JWT del cual extraer los reclamos
     * @return the Claims object containing all claims
     *         el objeto Claims que contiene todos los reclamos
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token).getPayload();
    }

    /**
     * Retrieves the signing key from the application properties.
     * Recupera la clave de firma de las propiedades de la aplicación.
     *
     * @return the SecretKey used for signing the JWT token
     *         la clave secreta utilizada para firmar el token JWT
     */
    private SecretKey getSigningKey() {
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Checks if the token is present in the authorization parameter.
     * Verifica si el token está presente en el parámetro de autorización.
     *
     * @param authorizationParameter the authorization parameter string
     *                               la cadena del parámetro de autorización
     * @return true if the token is present, false otherwise
     *         verdadero si el token está presente, falso en caso contrario
     */
    private boolean isTokenPresentIn(String authorizationParameter) {
        return StringUtils.hasText(authorizationParameter);
    }

    /**
     * Checks if the authorization parameter contains a Bearer token.
     * Verifica si el parámetro de autorización contiene un token Bearer.
     *
     * @param authorizationParameter the authorization parameter string
     *                               la cadena del parámetro de autorización
     * @return true if the parameter starts with "Bearer ", false otherwise
     *         verdadero si el parámetro comienza con "Bearer ", falso en caso contrario
     */
    private boolean isBearerTokenIn(String authorizationParameter) {
        return authorizationParameter.startsWith(BEARER_TOKEN_PREFIX);
    }

    /**
     * Extracts the token from the authorization header parameter.
     * Extrae el token del parámetro del encabezado de autorización.
     *
     * @param authorizationHeaderParameter the authorization header string
     *                                     la cadena del encabezado de autorización
     * @return the JWT token as a String
     *         el token JWT como una cadena
     */
    private String extractTokenFrom(String authorizationHeaderParameter) {
        return authorizationHeaderParameter.substring(TOKEN_BEGIN_INDEX);
    }

    /**
     * Retrieves the authorization parameter from the HTTP request.
     * Recupera el parámetro de autorización de la solicitud HTTP.
     *
     * @param request the HttpServletRequest object
     *                el objeto HttpServletRequest
     * @return the authorization parameter as a String
     *         el parámetro de autorización como una cadena
     */
    private String  getAuthorizationParameterFrom(HttpServletRequest request) {
        return request.getHeader(AUTHORIZATION_PARAMETER_NAME);
    }

/**
 * Extracts the Bearer token from the HTTP request.
 * Extrae el token Bearer de la solicitud HTTP.
 *
 * @param request the HttpServletRequest object
 *                el objeto HttpServletRequest
 * @return the JWT token as a String, or null if not present
 *         el token JWT como una cadena, o null si no está
 */
 @Override
    public String getBearerTokenFrom(HttpServletRequest request) {
        String parameter = getAuthorizationParameterFrom(request);
        if (isTokenPresentIn(parameter) && isBearerTokenIn(parameter)) {
            return extractTokenFrom(parameter);
        }
        return null;
    }
}
