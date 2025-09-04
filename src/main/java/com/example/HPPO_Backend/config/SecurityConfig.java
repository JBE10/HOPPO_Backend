package com.example.HPPO_Backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

        private final JwtAuthenticationFilter jwtAuthFilter;
        private final AuthenticationProvider authenticationProvider;

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http
                        .csrf(AbstractHttpConfigurer::disable)
                        .sessionManagement(sm -> sm.sessionCreationPolicy(STATELESS))
                        .authorizeHttpRequests(auth -> auth

                                .requestMatchers("/api/v1/auth/**", "/error/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/products/**", "/categories/**", "/brands/**").permitAll()


                                .requestMatchers(HttpMethod.POST,   "/products/**", "/categories/**", "/brands/**").hasRole("VENDEDOR")
                                .requestMatchers(HttpMethod.PUT,    "/products/**", "/categories/**", "/brands/**").hasRole("VENDEDOR")
                                .requestMatchers(HttpMethod.DELETE, "/products/**", "/categories/**", "/brands/**").hasRole("VENDEDOR")


                                .requestMatchers(HttpMethod.POST, "/orders/**").hasRole("COMPRADOR")
                                .requestMatchers(HttpMethod.GET, "/carts/**").hasRole("COMPRADOR")
                                .requestMatchers("/carts/**", "/cart-products/**").hasRole("COMPRADOR")


                                .requestMatchers(HttpMethod.GET,  "/orders/**").hasAnyRole("COMPRADOR", "VENDEDOR")

                                .anyRequest().authenticated()
                        )
                        .authenticationProvider(authenticationProvider)
                        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

                return http.build();
        }
}