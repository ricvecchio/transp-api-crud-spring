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

import java.io.IOException;
import java.util.ArrayList;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    @Autowired
    TokenService tokenService;
    @Autowired
    UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var token = this.recoverToken(request);
        var login = tokenService.validateToken(token);

        System.out.println("🔐 Request URI: " + request.getRequestURI()); //EXCLUIR
        System.out.println("🔐 Token: " + token); //EXCLUIR
        System.out.println("🔐 Login extraído do token: " + login); //EXCLUIR

        if (login != null) {
            User user = userRepository.findByUsername(login).orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));

            System.out.println("✅ Usuário encontrado: " + user.getUsername());  //EXCLUIR
            System.out.println("✅ Permissão do usuário: " + user.getPermission());  //EXCLUIR

            var authorities = new ArrayList<SimpleGrantedAuthority>();
            if (user.getPermission() != null) {
                authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getPermission()));
            }
            var authentication = new UsernamePasswordAuthenticationToken(user, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");

        if (authHeader == null) return null;
        return authHeader.replace("Bearer ", "");
    }
}
