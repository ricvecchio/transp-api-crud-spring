package com.transportadora.user.service;

import com.transportadora.user.dto.CreateUserRoleDTO;
import com.transportadora.user.entities.Role;
import com.transportadora.user.entities.User;
import com.transportadora.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CreateRoleUserService {

    @Autowired
    UserRepository userRepository;

    public User execute(CreateUserRoleDTO createUserRoleDTO) {

        Optional<User> userExists = userRepository.findById(createUserRoleDTO.getIdUser());
        List<Role> roles = new ArrayList<>();

        if (userExists.isEmpty()) {
            throw new Error("Usuário não existe!");
        }

        roles = createUserRoleDTO.getIdsRoles().stream().map(role -> {
            return new Role((UUID) role);
        }).collect(Collectors.toList());

        User user = userExists.get();

        user.setRoles(roles);

        userRepository.save(user);

        return user;

    }
}
