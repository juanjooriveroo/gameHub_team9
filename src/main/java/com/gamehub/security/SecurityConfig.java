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
                        .requestMatchers("/auth/**").permitAll()

                        .requestMatchers("/users/me").hasAnyRole("PLAYER", "ADMIN")
                        .requestMatchers("/users/{id}").permitAll()

                        .requestMatchers(HttpMethod.GET, "/tournaments").permitAll()
                        .requestMatchers(HttpMethod.GET, "/tournaments/{id}").permitAll()
                        .requestMatchers(HttpMethod.POST, "/tournaments").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/tournaments/{id}/join").hasRole("PLAYER")

                        .requestMatchers(HttpMethod.GET, "/matches/{id}").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/matches/{id}/result").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/matches/generate/{id}").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.GET, "/tournaments/{id}/messages").hasAnyRole("PLAYER", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/tournaments/{id}/messages").hasAnyRole("PLAYER", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/matches/{id}/messages").hasAnyRole("PLAYER", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/matches/{id}/messages").hasAnyRole("PLAYER", "ADMIN")

                        .requestMatchers("/tournaments/{id}/ranking").permitAll()

                        .requestMatchers("/swagger-ui/**").permitAll()
                        .requestMatchers("/api-docs/**").permitAll()
                        .requestMatchers("/swagger-ui.html").permitAll()
                        .requestMatchers("/v3/api-docs/**").permitAll()
                        .requestMatchers("/actuator/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                ).addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

}

