package com.transportadora.management.service;

import com.transportadora.management.dto.ClienteGastoDTO;
import com.transportadora.management.dto.GastoMensalDTO;
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
        // Obter os top 5 clientes por mês
        List<Object[]> topClientesResult = pedidoRepository.findTop5ClientesPorMesNative();

        // Obter o total de cada mês
        List<Object[]> totaisPorMesResult = pedidoRepository.findTotaisPorMes();

        Map<String, List<ClienteGastoDTO>> clientesPorMes = new HashMap<>();

        for (Object[] row : topClientesResult) {
            Long idCliente = ((Number) row[0]).longValue();
            Double precoTotal = ((Number) row[1]).doubleValue();
            Integer mes = ((Number) row[2]).intValue();
            Integer ano = ((Number) row[3]).intValue();

            String chave = ano + "-" + mes;
            clientesPorMes
                    .computeIfAbsent(chave, k -> new ArrayList<>())
                    .add(new ClienteGastoDTO(idCliente, precoTotal));
        }

        // Resposta final: lista de meses com total de gasto e top 5 clientes
        List<GastoMensalDTO> resposta = new ArrayList<>();

        for (Object[] row : totaisPorMesResult) {
            Integer mes = ((Number) row[0]).intValue();
            Integer ano = ((Number) row[1]).intValue();
            Double totalMes = ((Number) row[2]).doubleValue();
            String chave = ano + "-" + mes;

            List<ClienteGastoDTO> clientes = clientesPorMes.getOrDefault(chave, new ArrayList<>());

            // Montando o DTO para cada mês
            resposta.add(new GastoMensalDTO(ano, mes, totalMes, clientes));
        }

        return ResponseEntity.ok(resposta);
    }

}

