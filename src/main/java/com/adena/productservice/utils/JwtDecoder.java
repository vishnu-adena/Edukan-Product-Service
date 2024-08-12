package com.adena.productservice.utils;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

public class JwtDecoder {

    public long getUserId(){
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Jwt jwt = (Jwt) securityContext.getAuthentication().getPrincipal();
        return jwt.getClaim("userId");
    }
}
