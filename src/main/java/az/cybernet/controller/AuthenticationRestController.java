package az.cybernet.controller;

import az.cybernet.security.JwtAuthenticationRequest;
import az.cybernet.service.authenticate.AuthenticateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;

@RestController
@CrossOrigin("*")
@Api("endpoint for authenticate user")
public class AuthenticationRestController {

    private final AuthenticateService authenticateService;

    public AuthenticationRestController(AuthenticateService authenticateService) {
        this.authenticateService = authenticateService;
    }


    @ApiOperation("create authenticate token for user")
    @PostMapping("${jwt.route.authentication.path}")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest) throws AuthenticationException {
        return ResponseEntity.ok(authenticateService.generateToken(authenticationRequest));
    }
}
