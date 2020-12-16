package com.reselly.authorizationservice.api;

import com.reselly.authorizationservice.entity.Credentials;
import com.reselly.authorizationservice.service.JwtIssuerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authorize")
public class JwtController {

    private JwtIssuerService jwtService;

    @Autowired
    public JwtController(JwtIssuerService jwtService) {
        this.jwtService = jwtService;
    }

    /**
     * registers the user then issues their jwt
     * @param credentials the user credentials to persist
     * @return the user's jwt
     */
    @CrossOrigin
    @PostMapping("/register")
    public String createJwt(@RequestBody Credentials credentials){
        System.out.println("Created user " + credentials.getEmail());
        System.out.println("Added something for SQ check");
        return jwtService.generateToken(credentials);
    }

    /**
     * validates the incoming jwt token against the registered credentials
     * @param token the jwt token from Headers
     * @return whether the jwt is valid or not
     */
    @CrossOrigin
    @GetMapping("/validate")
    public boolean validateJwt(@RequestHeader(value = "Authorization") String token){
        return jwtService.validateToken(token);
    }

    /**
     * verified the jwt, if present, then authenticates the user
     * @param token the possible auth-token
     * @param credentials the user credentials
     * @return the re-issued jwt
     */
    @CrossOrigin
    @PostMapping("/login")
    public String loginAndReturnJwt(@RequestHeader(value = "Authorization") String token, @RequestBody Credentials credentials){
        return jwtService.loginUser(token, credentials);
    }
}
