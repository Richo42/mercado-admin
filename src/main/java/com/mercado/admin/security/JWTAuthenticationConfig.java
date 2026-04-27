package com.mercado.admin.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import java.util.Date;
import java.util.List;
import static com.mercado.admin.security.Constants.*;

@Configuration
public class JWTAuthenticationConfig {

    public String getJWTToken(String username, String role) {
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList(role);

        String token = Jwts.builder()
                .setId("mercado-admin")
                .setSubject(username)
                .claim("authorities", grantedAuthorities.stream()
                        .map(GrantedAuthority::getAuthority)
                        .toList())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_VALIDITY_SECONDS))
                .signWith(getSigningKey(SUPER_SECRET_TEXT), SignatureAlgorithm.HS256)
                .compact();

        return TOKEN_PREFIX + token;
    }

    public Claims decodeToken(String token){
        return Jwts.parser()
                .setSigningKey(getSigningKey(SUPER_SECRET_TEXT))
                .parseClaimsJws(token)
                .getBody();
    }
}