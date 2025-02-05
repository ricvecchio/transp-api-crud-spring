package com.transportadora.user.service;

import com.transportadora.user.entities.User;
import com.transportadora.user.repository.UserRepository;
import com.transportadora.user.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> existsUser = userRepository.findByUsername(username);
//        User existsUser = userRepository.findByUsernameFetchRoles(username);
//        if (existsUser == null) {
//            throw new UsernameNotFoundException("Usuário não encontrado!");
//        }

        User user = existsUser.orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado!"));
        System.out.println("AQUI - loadUserByUsername existsUser: " + existsUser); // EXCLUIR
        System.out.println("AQUI - loadUserByUsername user: " + user); // EXCLUIR

        return UserPrincipal.create(user);
    }

}
