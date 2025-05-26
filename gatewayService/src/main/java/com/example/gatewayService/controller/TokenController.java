package com.example.gatewayService.controller;

//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.security.oauth2.jwt.Jwt;
//import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class TokenController {


    // Whoami endpoint
//    @GetMapping("/whoami")
//    public WhoAmIResponse whoAmI(@AuthenticationPrincipal Jwt jwt) {
//        String username = jwt.getClaim("preferred_username");
//        String email = jwt.getClaim("email");
//        Collection<String> roles = jwt.getClaimAsStringList("roles");
//
//        return new WhoAmIResponse(username, email, roles);
//    }
//
//    record WhoAmIResponse(String username, String email, Collection<String> roles) {}
}
