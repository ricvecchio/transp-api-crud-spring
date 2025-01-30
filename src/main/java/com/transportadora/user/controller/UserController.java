package com.transportadora.user.controller;

import com.transportadora.user.dto.CreateUserRoleDTO;
import com.transportadora.user.entities.User;
import com.transportadora.user.service.CreateRoleUserService;
import com.transportadora.user.service.CreateUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    CreateUserService createUserService;

    @Autowired
    CreateRoleUserService createRoleUserService;

    @PostMapping("/create")
    public User create(@RequestBody User user) {
        return createUserService.execute(user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/role")
    public User role(@RequestBody CreateUserRoleDTO createUserRoleDTO) {
        return createRoleUserService.execute(createUserRoleDTO);
    }

    @PostMapping("/login")
    public String login() {
        return "Endpoint /login é público.";
    }

}
