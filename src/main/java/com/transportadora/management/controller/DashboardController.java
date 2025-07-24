package com.transportadora.management.controller;

import com.transportadora.management.dto.DashboardDTO;
import com.transportadora.management.service.ClienteService;
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
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

//    @GetMapping
//    public ResponseEntity<?> dashboard(
//            @RequestParam(defaultValue = "0") @PositiveOrZero int page,
//            @RequestParam(defaultValue = "10") @Positive @Max(100) int pageSize,
//            @RequestParam(required = false) String filtro) {
//
//        return dashboardService.dashboard(page, pageSize, filtro);
//    }

    @GetMapping
    public DashboardDTO dashboard(
            @RequestParam(defaultValue = "0") @PositiveOrZero int page,
            @RequestParam(defaultValue = "10") @Positive @Max(100) int pageSize,
            @RequestParam(defaultValue = "") String filter) {
        System.out.println("[DashboardController] Entrou aqui"); //EXCLUIR

        return dashboardService.dashboard(page, pageSize, filter);
    }

}