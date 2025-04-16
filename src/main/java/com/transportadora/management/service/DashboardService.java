package com.transportadora.management.service;

import com.transportadora.management.dto.TopClientesPorMesDTO;
import com.transportadora.management.repository.PedidoRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Validated
@Service
public class DashboardService {

    private final PedidoRepository pedidoRepository;

    public DashboardService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    @Cacheable(value = "Pedidos")
    public ResponseEntity<?> dashboard(int page, int pageSize, String filtro) {
        List<Object[]> resultados = pedidoRepository.findTop5ClientesPorMesNative();

        // Mapeia o resultado para uma lista de TopClientesPorMesDTO
        List<TopClientesPorMesDTO> topClientes = new ArrayList<>();
        for (Object[] resultado : resultados) {
            Long idCliente = ((Number) resultado[0]).longValue();
            Double precoTotal = ((Number) resultado[1]).doubleValue();
            Integer mesTotal = ((Number) resultado[2]).intValue();
            Integer anoTotal = ((Number) resultado[3]).intValue();

            TopClientesPorMesDTO dto = new TopClientesPorMesDTO(idCliente, precoTotal, mesTotal, anoTotal);
            topClientes.add(dto);
        }

        return ResponseEntity.ok(topClientes);
    }
}

