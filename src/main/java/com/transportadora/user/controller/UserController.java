package com.transportadora.user.controller;

import com.transportadora.user.dto.*;
import com.transportadora.user.entities.User;
import com.transportadora.user.repository.UserRepository;
import com.transportadora.user.security.TokenService;
import com.transportadora.user.service.PasswordRecoveryService;
import com.transportadora.user.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Validated
@RestController
@CrossOrigin(origins = "https://saotomecatimesaotomecatime.com")
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final UserService userService;
    private final PasswordRecoveryService passwordRecoveryService;

    @GetMapping("/list")
    public UserPaginacaoDTO list(
            @RequestParam(defaultValue = "0") @PositiveOrZero int page,
            @RequestParam(defaultValue = "10") @Positive @Max(100) int pageSize,
            @RequestParam(defaultValue = "") String filter) {
        return userService.list(page, pageSize, filter);
    }

    @GetMapping("/{username}")
    public Optional<User> findByUsername(@PathVariable @NotNull String username) {
        return userRepository.findByUsername(username);
    }

    @PutMapping("/{idUser}")
    public UserDTO update(@PathVariable @NotNull String idUser, @RequestBody @Valid UserDTO user) {
        return userService.update(idUser, user);
    }

    @DeleteMapping("/{idUser}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable @NotNull String idUser) {
        userService.delete(idUser);
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequestDTO body) {
        User user = this.userRepository.findByUsername(body.username()).orElseThrow(() -> new RuntimeException("Usuário não encotrado!"));
        if (passwordEncoder.matches(body.password(), user.getPassword())) {
            String token = this.tokenService.generateToken(user);
            return ResponseEntity.ok(new ResponseDTO(user.getUsername(), token));
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/recoverPassword")
    public ResponseEntity<Map<String, String>> recoverPassword(@RequestBody RecoveryPasswordDTO body) {
        passwordRecoveryService.sendRecoveryEmail(body.email(), body.username());
        return ResponseEntity.ok(Collections.singletonMap("message", "E-mail de recuperação enviado!"));

    }

    @PostMapping("/resetPassword")
    public ResponseEntity<Map<String, String>> resetPassword(@RequestBody ResetPasswordDTO body) {
        passwordRecoveryService.resetPassword(body.token(), body.newPassword());
        return ResponseEntity.ok(Collections.singletonMap("message", "Senha redefinida com sucesso!"));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterRequestDTO body) {
        Optional<User> user = this.userRepository.findByUsername(body.username());

        if (user.isEmpty()) {
            User newUser = new User();
            newUser.setName(body.name());
            newUser.setEmail(body.email());
            newUser.setUsername(body.username());
            newUser.setPassword(passwordEncoder.encode(body.password()));
            this.userRepository.save(newUser);

            String token = this.tokenService.generateToken(newUser);
            return ResponseEntity.ok(new ResponseDTO(newUser.getUsername(), token));
        }
        return ResponseEntity.badRequest().build();
    }

}
