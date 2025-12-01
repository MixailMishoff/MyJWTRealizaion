package com.security.TryingToMakeCorrectRealizationOfJWT.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {
    private final CustomUserDetailService customUserDetailService;
    private final JWTService jwtService;

    /**
     * Извлекает заголовок Authorization из Header
     * Проверяет присутствует ли заголовок в Bearer token
     * Если присутствует, то извлекаем email и создаем токен
     * так же меняет настройку аутентификации в контексте безопасности
     * с использованием созданного токена
     *
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && !authHeader.isBlank() && authHeader.startsWith("Bearer ")) {
            String jwt = authHeader.substring(7);
            if (jwt == null || jwt.isBlank()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid JWT Token in Bearer Header");
            } else {
                try {
                    String email = jwtService.validateTokenAndRetrieveSubject(jwt);
                    UserDetails userDetails = customUserDetailService.loadUserByUsername(email);
                    UsernamePasswordAuthenticationToken authtoken = new UsernamePasswordAuthenticationToken(email, userDetails.getPassword(), userDetails.getAuthorities());

                    if (SecurityContextHolder.getContext().getAuthentication() == null) {
                        SecurityContextHolder.getContext().setAuthentication(authtoken);
                    }
                } catch (JWTVerificationException e) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid JWT Token");
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}
