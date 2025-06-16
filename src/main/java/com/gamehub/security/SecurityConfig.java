package com.gamehub.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;


@Configuration
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,
                                           JwtAuthenticationFilter jwtAuthenticationFilter)
            throws Exception {
            http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()

                        .requestMatchers("/api/user/me").hasAnyRole("PLAYER", "ADMIN")
                        .requestMatchers("/api/user/{id}").permitAll()

                        .requestMatchers(HttpMethod.GET, "/api/tournaments").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/tournaments/{id}").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/tournaments").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/tournaments/{id}/join").hasRole("PLAYER")

                        .requestMatchers(HttpMethod.GET, "/api/tournaments/{id}/messages").hasAnyRole("PLAYER", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/tournaments/{id}/messages").hasAnyRole("PLAYER", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/matches/{id}/messages").hasAnyRole("PLAYER", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/matches/{id}/messages").hasAnyRole("PLAYER", "ADMIN")

                        .requestMatchers("/api/tournaments/{id}/ranking").permitAll()

                        .requestMatchers("/api/swagger-ui/**").permitAll()
                        .requestMatchers("/api/api-docs/**").permitAll()
                        .requestMatchers("/api/swagger-ui.html").permitAll()
                        .requestMatchers("/api/v3/api-docs/**").permitAll()
                        .requestMatchers("/api/actuator/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                ).addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

}

