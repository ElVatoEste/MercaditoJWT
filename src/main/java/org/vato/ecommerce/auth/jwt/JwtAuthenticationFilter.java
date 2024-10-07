package org.vato.ecommerce.auth.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;  // Usa JwtService para extraer y validar el JWT

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 1. Extraer el JWT desde el encabezado
        final String token = getTokenFromRequest(request);

        // 2. Validar el token
        if (token != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Extraer los detalles del usuario directamente desde el token (sin ir a la base de datos)
            UserDetails userDetails = jwtService.getUserDetailsFromToken(token);

            // Verificar si el token es válido
            if (jwtService.isTokenValid(token, userDetails)) {
                // Crear el objeto de autenticación
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()  // Authorities extraídas desde el JWT
                );

                // Establecer detalles adicionales de la solicitud
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Establecer la autenticación en el contexto de seguridad
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // Pasar la solicitud al siguiente filtro
        filterChain.doFilter(request, response);
    }

    /**
     * Extrae el token JWT del encabezado Authorization (Bearer token).
     */
    private String getTokenFromRequest(HttpServletRequest request) {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);  // Eliminar "Bearer " del token
        }
        return null;
    }
}
