package com.transportadora.management.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Collections;

@Validated
@Service
public class DashboardService {

    @Cacheable(value = "Pedidos")
    public ResponseEntity<?> dashboard(int page, int tamPagina, String filtro) {
        return ResponseEntity.ok(Collections.singletonMap("message", "Em desenvolvimento!"));
    }

}
