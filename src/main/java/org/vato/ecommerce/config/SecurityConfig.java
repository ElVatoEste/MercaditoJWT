package org.vato.ecommerce.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.vato.ecommerce.auth.jwt.JwtAuthenticationFilter;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authProvider;

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // Permitir todos los orígenes en desarrollo, pero deberías restringir esto en producción
        configuration.setAllowedOrigins(List.of("*"));  // Cambia a dominios confiables en producción
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))  // CORS configurado
                .csrf(csrf -> csrf.disable())  // Deshabilitar CSRF en JWT-based auth
                .authorizeHttpRequests(authRequest -> authRequest
                        // Permitir acceso a Swagger sin autenticación (puedes restringirlo en producción)
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()

                        // Permitir acceso sin autenticación a las rutas de autenticación (como login, registro)
                        .requestMatchers("/auth/**").permitAll()

                        // Endpoints de la API protegidos con JWT (ejemplo: solo accesible para usuario authenticados)
                        .requestMatchers("/api/**").hasAnyAuthority("Admin", "User")

                        // Endpoints solo accesible para usuario con el rol de admin
                        .requestMatchers("/admin/**").hasAuthority("Admin")

                        // Cualquier otra solicitud requiere autenticación
                        .anyRequest().authenticated()
                )
                .sessionManagement(sessionManager -> sessionManager
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))  // Sin estado (stateless)
                .authenticationProvider(authProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
