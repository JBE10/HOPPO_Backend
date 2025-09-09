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
                                // --- Público / Auth ---
                                .requestMatchers("/api/v1/auth/**", "/auth/**", "/error/**").permitAll()

                                // --- Catálogo público ---
                                .requestMatchers(HttpMethod.GET, "/products/**", "/categories/**", "/brands/**").permitAll()

                                // --- Products (gestión) - solo VENDEDOR ---
                                .requestMatchers(HttpMethod.POST,   "/products/**").hasRole("VENDEDOR")
                                .requestMatchers(HttpMethod.PUT,    "/products/**").hasRole("VENDEDOR")
                                .requestMatchers(HttpMethod.DELETE, "/products/**").hasRole("VENDEDOR")

                                // --- Categories (gestión) - solo VENDEDOR ---
                                .requestMatchers(HttpMethod.POST,   "/categories/**").hasRole("VENDEDOR")
                                .requestMatchers(HttpMethod.PUT,    "/categories/**").hasRole("VENDEDOR")
                                .requestMatchers(HttpMethod.DELETE, "/categories/**").hasRole("VENDEDOR")

                                // --- Brands (gestión) - solo VENDEDOR ---
                                .requestMatchers(HttpMethod.POST,   "/brands/**").hasRole("VENDEDOR")
                                .requestMatchers(HttpMethod.DELETE, "/brands/**").hasRole("VENDEDOR")
                                // (No definiste PUT en Brands, por eso no lo matcheo)

                                // --- Carts ---
                                // COMPRADOR: su carrito por endpoint dedicado + crear su carrito
                                .requestMatchers(HttpMethod.GET,  "/carts/my-cart").hasRole("COMPRADOR")
                                .requestMatchers(HttpMethod.POST, "/carts").hasRole("COMPRADOR")
                                // VENDEDOR: puede ver todos los carritos
                                .requestMatchers(HttpMethod.GET,  "/carts", "/carts/*").hasRole("VENDEDOR")
                                // (Si luego agregás PUT/DELETE de carts, restringilos a COMPRADOR y validá ownership en service)

                                // --- Cart-Products (líneas del carrito) ---
                                // Lectura: ambos roles (ownership del COMPRADOR en service)
                                .requestMatchers(HttpMethod.GET, "/cart-products/**").hasAnyRole("VENDEDOR", "COMPRADOR")
                                // Mutaciones: solo COMPRADOR (ownership en service)
                                .requestMatchers(HttpMethod.POST,   "/cart-products").hasRole("COMPRADOR")
                                .requestMatchers(HttpMethod.DELETE, "/cart-products/*").hasRole("COMPRADOR")

                                // --- Orders ---
                                // VENDEDOR: ver listados y detalle
                                .requestMatchers(HttpMethod.GET, "/orders", "/orders/*").hasRole("VENDEDOR")
                                // COMPRADOR: crear y cancelar
                                .requestMatchers(HttpMethod.POST,  "/orders").hasRole("COMPRADOR")
                                .requestMatchers(HttpMethod.PATCH, "/orders/*/cancel").hasRole("COMPRADOR")

                                // --- Resto autenticado ---
                                .anyRequest().authenticated()
                        )
                        .authenticationProvider(authenticationProvider)
                        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

                return http.build();
        }
}