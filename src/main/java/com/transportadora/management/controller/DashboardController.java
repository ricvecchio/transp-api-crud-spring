package com.transportadora.management.controller;

import com.transportadora.management.service.DashboardService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
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

    private static final Logger logger = LoggerFactory.getLogger(DashboardController.class); // EXCLUIR

    @GetMapping
    public ResponseEntity<?> dashboard(
            @RequestParam(defaultValue = "0") @PositiveOrZero int page,
            @RequestParam(defaultValue = "10") @Positive @Max(100) int pageSize,
            @RequestParam(required = false) String filtro) {

        logger.info("[DashboardController] Acesso recebido. Page: {}, pageSize: {}, filtro: {}", page, pageSize, filtro);  // EXCLUIR
        return dashboardService.dashboard(page, pageSize, filtro);
    }

}
