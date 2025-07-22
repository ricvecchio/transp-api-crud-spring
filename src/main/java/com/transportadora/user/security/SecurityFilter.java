package com.transportadora.user.security;

import com.transportadora.user.entities.User;
import com.transportadora.user.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    @Autowired
    TokenService tokenService;
    @Autowired
    UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(SecurityFilter.class); //EXCLUIR

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var token = this.recoverToken(request);
        var login = tokenService.validateToken(token);

        logger.info("Request URI: {}", request.getRequestURI()); //EXCLUIR
        logger.info("Token: {}", token); //EXCLUIR
        logger.info("Login extraído do token: {}", login); //EXCLUIR

        if (login != null) {
            User user = userRepository.findByUsername(login).orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));

            logger.info("Usuário encontrado: {}", user.getUsername()); //EXCLUIR
            logger.info("Permissão do usuário: {}", user.getPermission()); //EXCLUIR

            var authorities = new ArrayList<SimpleGrantedAuthority>();
            if (user.getPermission() != null) {
                var role = "ROLE_" + user.getPermission(); // EXCLUIR

                authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getPermission()));
                logger.info("[SecurityFilter] GrantedAuthority aplicada: {}", role); //EXCLUIR

            }
            var authentication = new UsernamePasswordAuthenticationToken(user, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            logger.info("[SecurityFilter] SecurityContext atualizado com autenticação."); // EXCLUIR
        } else { // EXCLUIR
            logger.warn("[SecurityFilter] Token inválido ou ausente."); // EXCLUIR

        }
        filterChain.doFilter(request, response);
    }

//    private String recoverToken(HttpServletRequest request) {
//        var authHeader = request.getHeader("Authorization");
//
//        if (authHeader == null) return null;
//        return authHeader.replace("Bearer ", "");
//    }

    // EXCLUIR METODO ABAIXO E RETORNAR O DE CIMA
private String recoverToken(HttpServletRequest request) {
    String authHeader = request.getHeader("Authorization");
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
        logger.warn("[SecurityFilter] Authorization header ausente ou malformado.");
        return null;
    }
    return authHeader.replace("Bearer ", "");
}
}
