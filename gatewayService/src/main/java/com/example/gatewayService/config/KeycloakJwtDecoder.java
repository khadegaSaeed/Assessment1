package com.example.gatewayService.config;

//import org.springframework.security.oauth2.jwt.Jwt;
//import org.springframework.security.oauth2.jwt.JwtDecoder;
//import org.springframework.security.oauth2.jwt.JwtException;
//import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
//import java.util.Map;
//
//public class KeycloakJwtDecoder implements JwtDecoder {
//
//    private final JwtDecoder defaultJwtDecoder;
//
//    public KeycloakJwtDecoder(String jwksUri) {
//        this.defaultJwtDecoder = NimbusJwtDecoder.withJwkSetUri(jwksUri).build();
//    }
//
//    @Override
//    public Jwt decode(String token) throws JwtException {
//        // 1. Use default JWT validation
//        Jwt jwt = defaultJwtDecoder.decode(token);
//
//        // 2. (Optional) Add custom Keycloak-specific validations
//        validateTokenClaims(jwt.getClaims());
//
//        return jwt;
//    }
//
//    private void validateTokenClaims(Map<String, Object> claims) {
//        // Example: Check if "realm_access" exists (Keycloak-specific)
//        if (!claims.containsKey("realm_access")) {
//            throw new JwtException("Missing Keycloak realm access claims");
//        }
//
//        // Example: Verify issuer matches Keycloak realm
//        String issuer = (String) claims.get("iss");
//        if (!issuer.equals("http://localhost:8080/realms/your-realm")) {
//            throw new JwtException("Invalid issuer");
//        }
//    }
//}