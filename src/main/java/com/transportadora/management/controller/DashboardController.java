package com.transportadora.management.controller;

import com.transportadora.management.service.DashboardService;
import com.transportadora.user.dto.UserPaginacaoDTO;
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
//        System.out.println("DashboardController: Entrou aqui");  // EXCLUIR
//
//        return dashboardService.dashboard(page, pageSize, filtro);
//    }

    // EXCLUIR TUDO PARA BAIXO
    private final UserService userService;

//    @GetMapping("/list")
    @GetMapping
    public UserPaginacaoDTO list(
            @RequestParam(defaultValue = "0") @PositiveOrZero int page,
            @RequestParam(defaultValue = "10") @Positive @Max(100) int pageSize,
            @RequestParam(defaultValue = "") String filter) {
        System.out.println("[DashboardController] Entrou aqui"); //EXCLUIR

        return userService.list(page, pageSize, filter);
    }

}