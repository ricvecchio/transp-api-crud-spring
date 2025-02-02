package com.transportadora.user.controller;

import com.transportadora.user.dto.*;
import com.transportadora.user.entities.User;
import com.transportadora.user.repository.UserRepository;
import com.transportadora.user.security.TokenService;
import com.transportadora.user.service.CreateRoleUserService;
import com.transportadora.user.service.UserService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Validated
@RestController
@CrossOrigin(origins = "https://saotomecatime.onrender.com")
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final UserService userService;

    @Autowired
    CreateRoleUserService createRoleUserService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/role")
    public User role(@RequestBody CreateUserRoleDTO createUserRoleDTO) {
        return createRoleUserService.execute(createUserRoleDTO);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/list")
    public UserPaginacaoDTO list(
            @RequestParam(defaultValue = "0") @PositiveOrZero int page,
            @RequestParam(defaultValue = "10") @Positive @Max(100) int pageSize,
            @RequestParam(defaultValue = "") String filter) {
        return userService.list(page, pageSize, filter);
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequestDTO body) {
        User user = this.repository.findByUsername(body.username()).orElseThrow(() -> new RuntimeException("Usuário não encotrado!"));
        if (passwordEncoder.matches(body.password(), user.getPassword())) {
            String token = this.tokenService.generateToken(user);
            return ResponseEntity.ok(new ResponseDTO(user.getUsername(), token));
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterRequestDTO body) {
        Optional<User> user = this.repository.findByUsername(body.username());

        if (user.isEmpty()) {
            User newUser = new User();
            newUser.setName(body.name());
            newUser.setEmail(body.email());
            newUser.setUsername(body.username());
            newUser.setPassword(passwordEncoder.encode(body.password()));
            this.repository.save(newUser);

            String token = this.tokenService.generateToken(newUser);
            return ResponseEntity.ok(new ResponseDTO(newUser.getUsername(), token));
        }
        return ResponseEntity.badRequest().build();
    }

}
