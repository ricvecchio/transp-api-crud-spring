package com.transportadora.management.service;

import com.transportadora.management.dto.ClienteGastoDTO;
import com.transportadora.management.dto.DashboardDTO;
import com.transportadora.management.dto.GastoMensalDTO;
import com.transportadora.management.repository.PedidoRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Validated
@Service
public class DashboardService {

    private final PedidoRepository pedidoRepository;

    public DashboardService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

//    @Cacheable(value = "Pedidos")
//    public DashboardDTO dashboard(int page, int pageSize, String filter) {
//        System.out.println("[DashboardService] Entrou aqui /dashboard");
//
//        List<Object[]> topClientesResult = pedidoRepository.findTop5ClientesPorMesNative();
//        System.out.println("Resultado topClientesResult = " + (topClientesResult == null ? "null" : topClientesResult.size()));
//
//        List<Object[]> totaisPorMesResult = pedidoRepository.findTotaisPorMes();
//        System.out.println("Resultado totaisPorMesResult = " + (totaisPorMesResult == null ? "null" : totaisPorMesResult.size()));
//
//        Map<String, List<ClienteGastoDTO>> clientesPorMes = new HashMap<>();
//        System.out.println("Resultado clientesPorMes = " + clientesPorMes);
//
//        return null;
//    }

//    @Cacheable(value = "Pedidos")
//    public DashboardDTO dashboard(int page, int pageSize, String filter) {
//
//        List<Object[]> topClientesResult = pedidoRepository.findTop5ClientesPorMesNative();
//        List<Object[]> totaisPorMesResult = pedidoRepository.findTotaisPorMes();
//        Map<String, List<ClienteGastoDTO>> clientesPorMes = new HashMap<>();
//
//        for (Object[] row : topClientesResult) {
//            Long idCliente = ((Number) row[0]).longValue();
//            Double precoTotal = ((Number) row[1]).doubleValue();
//            Integer mes = ((Number) row[2]).intValue();
//            Integer ano = ((Number) row[3]).intValue();
//
//            String chave = ano + "-" + mes;
//            clientesPorMes
//                    .computeIfAbsent(chave, k -> new ArrayList<>())
//                    .add(new ClienteGastoDTO(idCliente, precoTotal));
//        }
//
//        List<GastoMensalDTO> resposta = new ArrayList<>();
//        for (Object[] row : totaisPorMesResult) {
//            Integer mes = ((Number) row[0]).intValue();
//            Integer ano = ((Number) row[1]).intValue();
//            Double totalMes = ((Number) row[2]).doubleValue();
//            String chave = ano + "-" + mes;
//
//            List<ClienteGastoDTO> clientes = clientesPorMes.getOrDefault(chave, new ArrayList<>());
//
//            resposta.add(new GastoMensalDTO(ano, mes, totalMes, clientes));
//        }
//        return new DashboardDTO(resposta);
//    }

    @Cacheable(value = "Pedidos")
    public DashboardDTO dashboard(int page, int pageSize, String filter) {

        // Busca dados crus (id_cliente, preco_final, data)
        List<Object[]> pedidos = pedidoRepository.findPedidosParaProcessamento();

        // Map<ano-mes, Map<idCliente, soma>>
        Map<String, Map<Long, Double>> somaPorClientePorMes = new HashMap<>();
        // Map<ano-mes, soma total>
        Map<String, Double> somaTotalPorMes = new HashMap<>();

        for (Object[] row : pedidos) {
            Long idCliente = ((Number) row[0]).longValue();
            String precoFinalStr = (String) row[1];
            Timestamp ts = (Timestamp) row[2];
            LocalDateTime data = ts.toLocalDateTime();

            int mes = data.getMonthValue();
            int ano = data.getYear();
            String chave = ano + "-" + mes;

            double valor = parsePreco(precoFinalStr);

            // soma por cliente
            somaPorClientePorMes
                    .computeIfAbsent(chave, k -> new HashMap<>())
                    .merge(idCliente, valor, Double::sum);

            // soma total do mês
            somaTotalPorMes.merge(chave, valor, Double::sum);
        }

        // Monta lista final
        List<GastoMensalDTO> resposta = new ArrayList<>();

        for (String chave : somaTotalPorMes.keySet()) {
            Double totalMes = somaTotalPorMes.get(chave);
            String[] partes = chave.split("-");
            Integer ano = Integer.parseInt(partes[0]);
            Integer mes = Integer.parseInt(partes[1]);

            Map<Long, Double> clientesMap = somaPorClientePorMes.getOrDefault(chave, Collections.emptyMap());

            // Pega top 5 clientes desse mês
            List<ClienteGastoDTO> top5Clientes = clientesMap.entrySet()
                    .stream()
                    .sorted((a, b) -> Double.compare(b.getValue(), a.getValue()))
                    .limit(5)
                    .map(e -> new ClienteGastoDTO(e.getKey(), e.getValue()))
                    .collect(Collectors.toList());

            resposta.add(new GastoMensalDTO(ano, mes, totalMes, top5Clientes));
        }

        // Ordena por ano e mês
        resposta.sort(Comparator.comparing(GastoMensalDTO::getAnoTotal)
                .thenComparing(GastoMensalDTO::getMesTotal));

        return new DashboardDTO(resposta);
    }

    private double parsePreco(String preco) {
        if (preco == null) return 0.0;
        String cleaned = preco
                .replaceAll("[\\s\\u00A0]", "")
                .replace("R$", "")
                .replace(".", "")
                .replace(",", ".");
        if (cleaned.isEmpty()) return 0.0;
        try {
            return Double.parseDouble(cleaned);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

}