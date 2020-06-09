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

    @PostMapping("/")
    public String createJwt(@RequestBody Credentials credentials){
        return jwtService.generateToken(credentials);
    }

    @GetMapping("/validate")
    public boolean validateJwt(@RequestParam("token") String token){
        return jwtService.validateToken(token);
    }
}
