package com.bridglelabz.addressbookapp.filter;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.Collections;

@Component
public class JwtFilter extends HttpFilter {

    private static final String SECRET_KEY = "YourSecretKeyForJWTGenerationMustBeAtLeast32CharactersLong"; // 256-bit key required

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        System.out.println(" JwtFilter Triggered ");

        if (request.getRequestURI().startsWith("/auth/") ||
                request.getRequestURI().startsWith("/error") ||
                request.getRequestURI().startsWith("/swagger-ui/") ||
                request.getRequestURI().startsWith("/v3/api-docs/")) {
            System.out.println("Bypassing filter for: " + request.getRequestURI());
            chain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");
        System.out.println("Auth Header: " + authHeader);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Access Denied: Valid token required to access this API.");
            return;
        }

        String token = authHeader.substring(7);
        try {
            SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

            Claims claims = Jwts.parser()
                    .setSigningKey(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            System.out.println("Decoded Claims: " + claims);

            String role = claims.get("role", String.class);
            if (role == null || role.trim().isEmpty()) {
                System.out.println(" Role is missing or empty in token.");
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Role in Token.");
                return;
            }

            System.out.println("Role Extracted: " + role);

            String requestURI = request.getRequestURI();
            System.out.println("Requested URI: " + requestURI);

            if (requestURI.startsWith("/addressbook") &&
                    !(role.equalsIgnoreCase("USER") || role.equalsIgnoreCase("ADMIN"))) {
                System.out.println("Role doesn't match USER or ADMIN.");
                response.sendError(HttpServletResponse.SC_FORBIDDEN,
                        "Access Denied: Insufficient permissions.");
                return;
            }

            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role);
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(null, null, Collections.singletonList(authority));

            SecurityContextHolder.getContext().setAuthentication(authToken);
            request.setAttribute("claims", claims);
            chain.doFilter(request, response);

        } catch (ExpiredJwtException e) {
            System.out.println("Token Expired: " + e.getMessage());
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token Expired. Please login again.");

        } catch (JwtException e) {
            System.out.println("Invalid Token Exception: " + e.getMessage());
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Access Denied: Invalid token provided.");
        }
    }
}
