package az.cybernet.service.authenticate;

import az.cybernet.security.JwtAuthenticationRequest;
import org.springframework.http.ResponseEntity;

import javax.naming.AuthenticationException;


public interface AuthenticateService {

    ResponseEntity<?> generateToken(JwtAuthenticationRequest jwtAuthenticationRequest) throws AuthenticationException;
}
