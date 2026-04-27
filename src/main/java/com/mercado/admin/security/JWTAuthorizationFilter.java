package com.mercado.admin.security;

import io.jsonwebtoken.*;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import static com.mercado.admin.security.Constants.*;

@Component
public class JWTAuthorizationFilter extends OncePerRequestFilter {

    private Claims extractClaims(HttpServletRequest request) {
        String jwtToken = request.getHeader(HEADER_AUTHORIZATION).replace(TOKEN_PREFIX, "");
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey(SUPER_SECRET_TEXT))
                .build()
                .parseClaimsJws(jwtToken)
                .getBody();
    }

    private void setAuthentication(Claims claims) {
        List<String> authorities = claims.get("authorities", List.class);
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                claims.getSubject(),
                null,
                authorities.stream().map(SimpleGrantedAuthority::new).toList()
        );
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    private boolean isJWTValid(HttpServletRequest request) {
        String authenticationHeader = request.getHeader(HEADER_AUTHORIZATION);
        return authenticationHeader != null && authenticationHeader.startsWith(TOKEN_PREFIX);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            if (isJWTValid(request)) {
                Claims claims = extractClaims(request);
                if (claims.get("authorities") != null) {
                    setAuthentication(claims);
                } else {
                    SecurityContextHolder.clearContext();
                }
            } else {
                SecurityContextHolder.clearContext();
            }
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
        }
    }
}