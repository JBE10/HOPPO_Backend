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

                                .requestMatchers("/api/v1/auth/**", "/auth/**", "/error/**").permitAll()


                                .requestMatchers(HttpMethod.GET, "/products/**", "/categories/**", "/brands/**").permitAll()


                                .requestMatchers(HttpMethod.POST,   "/products/**").hasRole("VENDEDOR")
                                .requestMatchers(HttpMethod.PUT,    "/products/**").hasRole("VENDEDOR")
                                .requestMatchers(HttpMethod.DELETE, "/products/**").hasRole("VENDEDOR")


                                .requestMatchers(HttpMethod.POST,   "/categories/**").hasRole("VENDEDOR")
                                .requestMatchers(HttpMethod.PUT,    "/categories/**").hasRole("VENDEDOR")
                                .requestMatchers(HttpMethod.DELETE, "/categories/**").hasRole("VENDEDOR")

                                .requestMatchers(HttpMethod.POST,   "/brands/**").hasRole("VENDEDOR")
                                .requestMatchers(HttpMethod.DELETE, "/brands/**").hasRole("VENDEDOR")



                                .requestMatchers(HttpMethod.GET,  "/carts/my-cart").hasRole("COMPRADOR")
                                .requestMatchers(HttpMethod.POST, "/carts").hasRole("COMPRADOR")

                                .requestMatchers(HttpMethod.GET,  "/carts", "/carts/*").hasRole("VENDEDOR")

                                .requestMatchers(HttpMethod.GET, "/cart-products/**").hasAnyRole("VENDEDOR", "COMPRADOR")

                                .requestMatchers(HttpMethod.POST,   "/cart-products").hasRole("COMPRADOR")
                                .requestMatchers(HttpMethod.DELETE, "/cart-products/*").hasRole("COMPRADOR")


                                .requestMatchers(HttpMethod.GET, "/orders", "/orders/*").hasRole("VENDEDOR")

                                .requestMatchers(HttpMethod.POST,  "/orders").hasRole("COMPRADOR")
                                .requestMatchers(HttpMethod.PATCH, "/orders/*/cancel").hasRole("COMPRADOR")


                                .anyRequest().authenticated()
                        )
                        .authenticationProvider(authenticationProvider)
                        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

                return http.build();
        }
}