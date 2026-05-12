package com.transportadora.user.security;

import com.transportadora.user.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .cors(cors -> {})
                .csrf(csrf -> csrf.disable())

                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                .authorizeHttpRequests(authorize -> authorize

                        // LIBERA PREFLIGHT CORS
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // LIBERA /error PARA DEBUG E TRATAMENTO
                        .requestMatchers("/error").permitAll()

                        // LOGIN / AUTH
                        .requestMatchers(HttpMethod.POST, "/api/users/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/users/register").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/users/recoverPassword").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/users/resetPassword").permitAll()

                        // DASHBOARD / USERS
                        .requestMatchers(HttpMethod.GET, "/api/users/list")
                        .hasAnyRole("ADMIN", "DESENV")

                        .requestMatchers(HttpMethod.GET, "/api/dashboard")
                        .hasAnyRole("ADMIN", "DESENV")

                        // CLIENTES
                        .requestMatchers("/api/clientes", "/api/clientes/**")
                        .hasAnyRole("USER", "ADMIN", "DESENV")

                        // PEDIDOS
                        .requestMatchers("/api/pedidos", "/api/pedidos/**")
                        .hasAnyRole("USER", "ADMIN", "DESENV")

                        .anyRequest().authenticated()
                )

                .addFilterBefore(
                        securityFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }
}