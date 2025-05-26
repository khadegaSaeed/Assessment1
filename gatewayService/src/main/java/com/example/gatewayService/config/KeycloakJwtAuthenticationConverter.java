package com.example.gatewayService.config;

//import org.springframework.core.convert.converter.Converter;
//import org.springframework.security.authentication.AbstractAuthenticationToken;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.oauth2.jwt.Jwt;
//import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
//import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
//
//import java.util.Collection;
//import java.util.Collections;
//import java.util.Map;
//import java.util.stream.Collectors;
//import java.util.stream.Stream;
//
//public class KeycloakJwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {
//
//    private final JwtGrantedAuthoritiesConverter defaultGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
//
//    @Override
//    public AbstractAuthenticationToken convert(Jwt jwt) {
//        Collection<GrantedAuthority> authorities = Stream
//                .concat(
//                        defaultGrantedAuthoritiesConverter.convert(jwt).stream(),
//                        extractResourceRoles(jwt).stream()
//                )
//                .collect(Collectors.toSet());
//
//        return new JwtAuthenticationToken(jwt, authorities, jwt.getClaim("preferred_username"));
//    }
//
//    private Collection<? extends GrantedAuthority> extractResourceRoles(Jwt jwt) {
//        Map<String, Object> resourceAccess = jwt.getClaim("resource_access");
//        Map<String, Object> resource;
//        Collection<String> resourceRoles;
//
//        if (resourceAccess == null
//                || (resource = (Map<String, Object>) resourceAccess.get("your-client-id")) == null
//                || (resourceRoles = (Collection<String>) resource.get("roles")) == null) {
//            return Collections.emptySet();
//        }
//
//        return resourceRoles.stream()
//                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
//                .collect(Collectors.toSet());
//    }
//}