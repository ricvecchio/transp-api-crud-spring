package com.transportadora.management.controller;

import com.transportadora.management.dto.DashboardDTO;
import com.transportadora.management.service.ClienteService;
import com.transportadora.management.service.DashboardService;
import com.transportadora.user.dto.UserPaginacaoDTO;
import com.transportadora.user.service.PasswordRecoveryService;
import com.transportadora.user.service.UserService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@CrossOrigin(origins = "https://saotomecatimesaotomecatime.com")
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

//    @GetMapping
//    public ResponseEntity<?> dashboard(
//            @RequestParam(defaultValue = "0") @PositiveOrZero int page,
//            @RequestParam(defaultValue = "10") @Positive @Max(100) int pageSize,
//            @RequestParam(required = false) String filtro) {
//
//        return dashboardService.dashboard(page, pageSize, filtro);
//    }

//    @GetMapping
    @GetMapping("/painel")
    public DashboardDTO painel(
            @RequestParam(defaultValue = "0") @PositiveOrZero int page,
            @RequestParam(defaultValue = "10") @Positive @Max(100) int pageSize,
            @RequestParam(defaultValue = "") String filter) {
        System.out.println("[DashboardController] Entrou aqui /painel"); //EXCLUIR

        return dashboardService.painel(page, pageSize, filter);
    }

    // EXCLUIR ABAIXO
//    private final UserService userService;
//
//    @GetMapping("/list")
//    public UserPaginacaoDTO list(
//            @RequestParam(defaultValue = "0") @PositiveOrZero int page,
//            @RequestParam(defaultValue = "10") @Positive @Max(100) int pageSize,
//            @RequestParam(defaultValue = "") String filter) {
//        System.out.println("[DashboardController] Entrou aqui /list"); //EXCLUIR
//
//        return userService.list(page, pageSize, filter);
//    }

    @GetMapping("/list")
    public DashboardDTO list(
            @RequestParam(defaultValue = "0") @PositiveOrZero int page,
            @RequestParam(defaultValue = "10") @Positive @Max(100) int pageSize,
            @RequestParam(defaultValue = "") String filter) {
        System.out.println("[DashboardController] Entrou aqui /list dash"); //EXCLUIR

        return dashboardService.painel(page, pageSize, filter);
    }

}