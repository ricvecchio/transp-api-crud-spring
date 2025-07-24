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

@Validated
@RestController
@CrossOrigin(origins = "https://saotomecatimesaotomecatime.com")
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

//    public DashboardController(DashboardService dashboardService) {
//        this.dashboardService = dashboardService;
//    }

    @GetMapping
    public ResponseEntity<?> dashboard(
            @RequestParam(defaultValue = "0") @PositiveOrZero int page,
            @RequestParam(defaultValue = "10") @Positive @Max(100) int pageSize,
            @RequestParam(required = false) String filtro) {
        System.out.println("DashboardController: Entrou aqui");  // EXCLUIR
        var auth = SecurityContextHolder.getContext().getAuthentication();  // EXCLUIR
        System.out.println("🔎 Controller: Usuário autenticado: " + auth.getName());  // EXCLUIR
        System.out.println("🔎 Controller: Authorities: " + auth.getAuthorities());  // EXCLUIR
        return dashboardService.dashboard(page, pageSize, filtro);
    }

}
