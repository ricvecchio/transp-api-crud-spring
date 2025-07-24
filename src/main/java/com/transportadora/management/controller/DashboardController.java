package com.transportadora.management.controller;

import com.transportadora.management.service.DashboardService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

//@Validated
//@RestController
//@CrossOrigin(origins = "https://saotomecatimesaotomecatime.com")
//@RequestMapping("/api/dashboard")
//@RequiredArgsConstructor
//public class DashboardController {
//
//    private final DashboardService dashboardService;
//
//    @GetMapping
//    public ResponseEntity<?> dashboard(
//            @RequestParam(defaultValue = "0") @PositiveOrZero int page,
//            @RequestParam(defaultValue = "10") @Positive @Max(100) int pageSize,
//            @RequestParam(required = false) String filtro) {
//        System.out.println("DashboardController: Entrou aqui");  // EXCLUIR
//        var auth = SecurityContextHolder.getContext().getAuthentication();  // EXCLUIR
//        System.out.println("🔎 Controller: Usuário autenticado: " + auth.getName());  // EXCLUIR
//        System.out.println("🔎 Controller: Authorities: " + auth.getAuthorities());  // EXCLUIR
//        return dashboardService.dashboard(page, pageSize, filtro);
//    }
//
//}

import com.transportadora.management.service.DashboardService;
import com.transportadora.user.dto.*;
import com.transportadora.user.entities.User;
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
import org.springframework.security.core.context.SecurityContextHolder;
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
public class DashboardController {

    private final UserService userService;
    private final PasswordRecoveryService passwordRecoveryService;

    @GetMapping("/list")
    public UserPaginacaoDTO list(
            @RequestParam(defaultValue = "0") @PositiveOrZero int page,
            @RequestParam(defaultValue = "10") @Positive @Max(100) int pageSize,
            @RequestParam(defaultValue = "") String filter) {
        return userService.list(page, pageSize, filter);
    }

}
